package com.taetae98.hilt.dialog

import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.hilt.R
import com.taetae98.hilt.base.BaseDialog
import com.taetae98.hilt.data.SummonerEntity
import com.taetae98.hilt.database.SummonerEntityRepository
import com.taetae98.hilt.databinding.DialogEditSummonerEntityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SummonerEntityEditDialog : BaseDialog<DialogEditSummonerEntityBinding>(R.layout.dialog_edit_summoner_entity) {
    @Inject
    lateinit var summonerEntityRepository: SummonerEntityRepository

    override fun onResume() {
        super.onResume()
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun init() {
        initOnOK()
    }

    private fun initOnOK() {
        with(binding) {
            setOnOK {
                val name = nameInputLayout.editText!!.text.toString()

                summonerEntityRepository.insertSummonerEntity(SummonerEntity(name))
                findNavController().navigateUp()
            }
        }
    }
}