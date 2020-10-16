package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databasefavorite.FavoriteDishesDao
import com.example.foodapp.databasefavorite.FavoriteDishesDatabase
import com.example.foodapp.databasenotes.Note
import com.example.foodapp.databasenotes.NoteDao
import com.example.foodapp.databasenotes.NoteDatabase
import com.example.foodapp.repositorydishbyid.DishByIdDataModel
import com.example.foodapp.repositorydishbyid.DishByIdDataModelMapper
import com.example.foodapp.repositorydishbyid.DishByIdRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dish_full_description.*
import kotlinx.android.synthetic.main.activity_dish_full_description.viewDishName
import kotlinx.android.synthetic.main.item_note.view.*
import okhttp3.OkHttpClient

const val KEY_EXTRA_DISH_ID = "KEY_EXTRA_DISH_ID"

class DishFullDescriptionActivity : AppCompatActivity(){

    private var disposable: Disposable? = null
    private lateinit var dishId: String
    private lateinit var favoriteDishesDao: FavoriteDishesDao
    private lateinit var noteDao: NoteDao
    private lateinit var dishByIdDataModel: DishByIdDataModel
    private lateinit var notesList: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_full_description)

        setSupportActionBar(toolbarDishFullDescriptionActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val favoriteDishesDatabase = FavoriteDishesDatabase.getDatabaseInstance(this)
        if(favoriteDishesDatabase != null){
            favoriteDishesDao = favoriteDishesDatabase.getFavoriteDishesDao()
        }

        val noteDatabase = NoteDatabase.getDatabaseInstance(this)
        if(noteDatabase != null){
            noteDao = noteDatabase.getNoteDao()
        }

        dishId = getDishId()
        fetchDishById()

        whetherIsFavorite(dishId)

        viewAddToFavorite.setOnClickListener {
            if(favoriteDishesDao.getById(dishId) == null){
                favoriteDishesDao.insert(dishByIdDataModel)
            }else{
                favoriteDishesDao.delete(dishByIdDataModel)
            }
        }

        viewCreateNote.setOnClickListener {
            showDialogCreateNote()
            ///////// + LiveData
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigateToFavoriteDishesActivity -> startFavoriteDishesActivity()
            android.R.id.home ->  finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFavoriteDishesActivity(){
        val instance = FavoriteDishesActivity.newInstance()
        val intent = instance.newIntent(this)
        startActivity(intent)
    }

    private fun whetherIsFavorite(dishId: String){
        if(favoriteDishesDao.getById(dishId) != null){
            viewAddToFavorite.setColorFilter(Color.RED)
        }
    }

    private fun getDishId(): String{
        val intent = intent
        return intent.getStringExtra(KEY_EXTRA_DISH_ID) as String
    }

    override fun onResume() {
        super.onResume()
        notesList = loadNotesList()
        initItemList()
    }

    private fun initItemList() {
        recyclerViewNotes.apply {
            adapter = NotesAdapter(notesList, object : OnNoteClickListener{
                override fun editNote(noteId: String) {
                    showDialogEditNote(noteId)
                    //// + LiveData
                }

            }, object : OnNoteLongClickListener {
                override fun deleteNote(noteId: String) {
                    showDialogDeleteNote(noteId)
                    //// + LiveData
                }
            })
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun loadNotesList(): List<Note>{
        return noteDao.getAll(dishId)
    }

    private fun fetchDishById() {
        disposable = DishByIdRepositoryImpl(
            okHttpClient = OkHttpClient(),
            dishByIdDataModelMapper = DishByIdDataModelMapper()
        ).getDishById(dishId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dish ->
                    with(dish){
                        viewDishName.text = dishName
                        viewDishCategory.text = categoryName
                        viewDishArea.text = areaName
                        viewDishIngredientsList.text = ingredients
                        viewInstruction.text = instructions

                        viewIconYouTube.setOnClickListener {
                            YouTubeActivity.newInstance().newIntent(this@DishFullDescriptionActivity)
                            startYouTubeActivity(this@DishFullDescriptionActivity, urlToVideo)
                        }

                        Glide.with(this@DishFullDescriptionActivity)
                            .load(urlToImage)
                            .into(viewDishPhoto)
                    }
                    dishByIdDataModel = dish
                },
                { throwable -> Log.d("DishFullDescriptionActivity", throwable.toString()) }
            )
    }

    private fun startYouTubeActivity(context: Context, urlToVideo: String){
        val instance = YouTubeActivity.newInstance()
        val intent = instance.newIntent(context)
        intent.putExtra(KEY_EXTRA_URL_TO_VIDEO, urlToVideo)
        startActivity(intent)
    }

    private fun showDialogCreateNote(){
        val noteTextField = EditText(this@DishFullDescriptionActivity)
        noteTextField.hint = getString(R.string.dialogCreateNoteHint)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        noteTextField.layoutParams = layoutParams

        val dialog = AlertDialog.Builder(this@DishFullDescriptionActivity, R.style.Widget_AppCompat_ButtonBar_AlertDialog)
        dialog
            .setTitle(R.string.createNote)
            .setView(noteTextField)
            .setPositiveButton(getString(R.string.dialogCreateNotePositiveButton),
                DialogInterface.OnClickListener { dialog, which ->
                    val newNote = Note(
                        dishId = dishId,
                        noteText = noteTextField.text.toString()
                    )
                    noteDao.insert(newNote)
                })
            .setNegativeButton(getString(R.string.dialogCreateNoteNegativeButton),
                DialogInterface.OnClickListener { dialog, which ->
                })
            .create()
        dialog.show()
    }

    private fun showDialogEditNote(noteId: String){
        val noteToEdit = noteDao.getById(noteId)
        val noteToEditText = noteToEdit.noteText
        val noteTextField = EditText(this@DishFullDescriptionActivity)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        noteTextField.layoutParams = layoutParams
        noteTextField.setText(noteToEditText)

        val dialog = AlertDialog.Builder(this@DishFullDescriptionActivity, R.style.Widget_AppCompat_ButtonBar_AlertDialog)
        dialog
            .setTitle(R.string.editNoteTitle)
            .setView(noteTextField)
            .setPositiveButton(getString(R.string.dialogEditNotePositiveButton),
                DialogInterface.OnClickListener { dialog, which ->
                    noteToEdit.noteText = noteTextField.text.toString()
                    noteDao.update(noteToEdit)
                })
            .setNegativeButton(getString(R.string.dialogEditNoteNegativeButton),
                DialogInterface.OnClickListener { dialog, which ->
                })
            .create()
        dialog.show()
    }

    private fun showDialogDeleteNote(noteId: String){
        val noteToDelete = noteDao.getById(noteId)
        val noteToDeleteText = noteToDelete.noteText
        val dialog = AlertDialog.Builder(this@DishFullDescriptionActivity, R.style.Widget_AppCompat_ButtonBar_AlertDialog)
        dialog
            .setTitle(R.string.deleteNoteDialogTitle)
            .setMessage(noteToDeleteText)
            .setPositiveButton(getString(R.string.dialogDeleteNotePositiveButton),
                DialogInterface.OnClickListener { dialog, which ->
                    noteDao.delete(noteToDelete)
                })
            .setNegativeButton(getString(R.string.dialogDeleteNoteNegativeButton),
                DialogInterface.OnClickListener { dialog, which ->
                })
            .create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        fun newInstance() = DishFullDescriptionActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, DishFullDescriptionActivity::class.java)
    }

    // ADAPTER
    class NotesAdapter(
        private val itemList: List<Note>,
        private val onNoteClickListener: OnNoteClickListener,
        private val onNoteLongClickListener: OnNoteLongClickListener
    ): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
            NotesViewHolder(
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
            )

        override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
            holder.bind(itemList[position], onNoteClickListener, onNoteLongClickListener)
        }

        override fun getItemCount(): Int = itemList.size

        // ViewHolder
        class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            fun bind(note: Note, onNoteClickListener: OnNoteClickListener,
                     onNoteLongClickListener: OnNoteLongClickListener){
                itemView.apply {
                    viewNoteText.text = note.noteText
                    setOnClickListener { onNoteClickListener.editNote(note.id) }
                    setOnLongClickListener {
                        onNoteLongClickListener.deleteNote(note.id)
                        return@setOnLongClickListener true
                    }
                }
            }
        }
    }
}