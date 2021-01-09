package com.taetae98.recyclerview.fragment

import com.taetae98.recyclerview.R
import com.taetae98.recyclerview.adapter.ChatAdapter
import com.taetae98.recyclerview.base.BaseFragment
import com.taetae98.recyclerview.data.Chat
import com.taetae98.recyclerview.databinding.FragmentChatBinding

class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {
    private val adapter by lazy { ChatAdapter().apply {
        submitList(list)
    }}

    private val list by lazy { ArrayList<Chat>().apply {
        add(Chat(0, "Hi", "Other"))
        add(Chat(1, "Nice to meet you!!"))
    }}

    override fun init() {
        super.init()
        initRecyclerView()
        initSendButton()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initSendButton() {
        binding.setSendAction {
            list.add(Chat(list.size.toLong(), binding.message.text.toString(), "My"))
            adapter.notifyItemInserted(list.lastIndex)
            binding.message.text.clear()
        }
    }
}