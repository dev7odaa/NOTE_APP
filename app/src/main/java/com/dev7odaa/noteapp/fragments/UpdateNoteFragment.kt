package com.dev7odaa.noteapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.dev7odaa.noteapp.MainActivity
import com.dev7odaa.noteapp.R
import com.dev7odaa.noteapp.databinding.FragmentUpdateNoteBinding
import com.dev7odaa.noteapp.model.Note
import com.dev7odaa.noteapp.toast
import com.dev7odaa.noteapp.viewModel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
                inflater,
                container,
                false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note!!
        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        binding.fabUpdate.setOnClickListener {

            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if(title.isNotEmpty()){
                val note = Note(currentNote.id, title, body)

                noteViewModel.addNote(note)

                view.findNavController().navigate(
                        R.id.action_updateNoteFragment_to_homeFragment
                )

            }else {
                activity?.toast("please enter title name!")

            }

        }


    }

    private fun deleteNote(){

        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure want to delete this note?")
            setPositiveButton("DELETE"){_,_ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                        R.id.action_updateNoteFragment_to_homeFragment
                )
            }

            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}