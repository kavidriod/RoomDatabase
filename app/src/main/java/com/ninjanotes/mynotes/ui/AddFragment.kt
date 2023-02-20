package com.ninjanotes.mynotes.ui

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.ninjanotes.mynotes.R
import com.ninjanotes.mynotes.db.Note
import com.ninjanotes.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : BaseFragment() {

    val TAG = "AddFragment"
    //private val note: Note? = null
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddFragmentArgs.fromBundle(it).note
            edittext_title.setText(note?.title)
            edittext_note.setText(note?.note)
        }

        button_save.setOnClickListener{ view ->
            val noteTitle = edittext_title.text.toString().trim()
            val noteBody = edittext_note.text.toString().trim()

            Log.d(TAG, "noteTitle: "+noteTitle)
            Log.d(TAG, "noteBody: "+noteBody)

            if (noteTitle.isEmpty()){
                edittext_title.error = "title required"
                edittext_title.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()){
                edittext_note.error = "note required"
                edittext_note.requestFocus()
                return@setOnClickListener
            }

            launch {
             //
                context?.let {
                    val mNote = Note(noteTitle,noteBody)
                    if (note == null){
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    }
                    else
                    {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }

                    val action = AddFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }
           /* val note = Note(noteTitle,noteBody)
            saveNoteThroughAsync(note)*/

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete -> if (note != null) deleteNote() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(){
     AlertDialog.Builder(context).apply {
         setTitle("Are you Sure?")
         setMessage("You cannot undo this Operation")
         setPositiveButton("Yes"){ _,_,->
             launch {
                 NoteDatabase(context).getNoteDao().deleteNote(note!!)
                 val action = AddFragmentDirections.actionSaveNote()
                 Navigation.findNavController(requireView()).navigate(action)
             }
         }
         setNegativeButton("No"){_,_,->

         }
     }.create().show()
    }

 /*   private fun saveNoteThroughAsync(note: Note){
        class SaveNote : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                NoteDatabase(requireActivity()).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(activity,"Note Saved", Toast.LENGTH_LONG).show()
            }
        }

        SaveNote().execute()
    }*/
}