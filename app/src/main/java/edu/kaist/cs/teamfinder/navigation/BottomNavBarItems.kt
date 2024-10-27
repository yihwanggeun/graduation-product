package edu.kaist.cs.teamfinder.navigation

import edu.kaist.cs.teamfinder.R

object BottomNavBarItems {

    val NavBarItems = listOf(
            NavBarItem(
                    name = "Home",
                    route = "home",
                    icon = R.drawable.home,
            ),
            NavBarItem(
                    name = "Feed",
                    route = "feed",
                    icon = R.drawable.connection,
            ),
        NavBarItem(
            name = "Add",
            route = "add",
            icon = R.drawable.add,
        ),
        NavBarItem(
            name = "Chat",
            route = "chat",
            icon = R.drawable.chat,
        ),
        NavBarItem(
            name = "Saved",
            route = "saved",
            icon = R.drawable.save,
        ),
    )
}