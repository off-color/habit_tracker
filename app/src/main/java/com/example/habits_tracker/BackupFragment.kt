package com.example.habits_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.habits_tracker.infrastructure.isConnectedToNetwork
import com.example.habits_tracker.ui.view_models.BackupViewModel
import kotlinx.android.synthetic.main.fragment_backup.*

class BackupFragment : Fragment() {

    private lateinit var backupViewModel: BackupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_backup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backupViewModel = BackupViewModel { activity?.isConnectedToNetwork() == true }

        backupViewModel.isSuccessLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                activity?.applicationContext,
                if (it) getString(R.string.backupDone) else getString(R.string.backupFailed),
                Toast.LENGTH_SHORT
            ).show()
        })

        downloadButton.setOnClickListener {
            backupViewModel.downloadHabitsFromServer()
        }

        uploadButton.setOnClickListener {
            backupViewModel.uploadHabitsToServer()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BackupFragment()
    }
}
