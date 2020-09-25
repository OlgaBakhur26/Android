package com.example.kotlin_room;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\b\u0010\u000b\u001a\u00020\tH\u0002J\u0012\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\tH\u0014J\b\u0010\u0010\u001a\u00020\tH\u0002J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/kotlin_room/ContactListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "contactDao", "Lcom/example/kotlin_room/database/ContactDao;", "contactsList", "", "Lcom/example/kotlin_room/database/Contact;", "checkEmpty", "", "list", "loadContactList", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "startAddContactActivity", "startEditContactActivity", "contact", "ContactsAdapter", "kotlin_room_debug"})
public final class ContactListActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.kotlin_room.database.ContactDao contactDao;
    private java.util.List<com.example.kotlin_room.database.Contact> contactsList;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void startAddContactActivity() {
    }
    
    private final void startEditContactActivity(com.example.kotlin_room.database.Contact contact) {
    }
    
    private final void checkEmpty(java.util.List<com.example.kotlin_room.database.Contact> list) {
    }
    
    private final void loadContactList() {
    }
    
    @java.lang.Override
    protected void onStart() {
    }
    
    public ContactListActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/example/kotlin_room/ContactListActivity$ContactsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/kotlin_room/ContactListActivity$ContactsAdapter$ContactsViewHolder;", "items", "", "Lcom/example/kotlin_room/database/Contact;", "contactListener", "Lcom/example/kotlin_room/OnContactClickListener;", "(Ljava/util/List;Lcom/example/kotlin_room/OnContactClickListener;)V", "getItems$kotlin_room_debug", "()Ljava/util/List;", "setItems$kotlin_room_debug", "(Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ContactsViewHolder", "kotlin_room_debug"})
    public static final class ContactsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.kotlin_room.ContactListActivity.ContactsAdapter.ContactsViewHolder> {
        @org.jetbrains.annotations.NotNull
        private java.util.List<com.example.kotlin_room.database.Contact> items;
        private final com.example.kotlin_room.OnContactClickListener contactListener = null;
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public com.example.kotlin_room.ContactListActivity.ContactsAdapter.ContactsViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.example.kotlin_room.ContactListActivity.ContactsAdapter.ContactsViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.example.kotlin_room.database.Contact> getItems$kotlin_room_debug() {
            return null;
        }
        
        public final void setItems$kotlin_room_debug(@org.jetbrains.annotations.NotNull
        java.util.List<com.example.kotlin_room.database.Contact> p0) {
        }
        
        public ContactsAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<com.example.kotlin_room.database.Contact> items, @org.jetbrains.annotations.NotNull
        com.example.kotlin_room.OnContactClickListener contactListener) {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0000\u00a2\u0006\u0002\b\u000b\u00a8\u0006\f"}, d2 = {"Lcom/example/kotlin_room/ContactListActivity$ContactsAdapter$ContactsViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "bind", "", "contact", "Lcom/example/kotlin_room/database/Contact;", "contactListener", "Lcom/example/kotlin_room/OnContactClickListener;", "bind$kotlin_room_debug", "kotlin_room_debug"})
        public static final class ContactsViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            
            public final void bind$kotlin_room_debug(@org.jetbrains.annotations.NotNull
            com.example.kotlin_room.database.Contact contact, @org.jetbrains.annotations.NotNull
            com.example.kotlin_room.OnContactClickListener contactListener) {
            }
            
            public ContactsViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View itemView) {
                super(null);
            }
        }
    }
}