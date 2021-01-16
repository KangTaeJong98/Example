package com.taetae98.room.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class DrawerWithToDo(
        @Embedded
        var drawer: Drawer = Drawer(),

        @Relation(
                parentColumn = "name",
                entityColumn = "drawerName"
        )
        val todoList: MutableList<ToDo> = mutableListOf()
)