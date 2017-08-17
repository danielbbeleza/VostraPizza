# VostraPizza
App for a Pizza Restaurant where you can order pizzas online, and track it's process.

There are two ways of selecting a pizza:
      1. You can do it by creating your own pizza, with the ingredients that are in the list. (ProjectYourPizza)
      2. You can pick a pizza from a menu, and select the correspondent quantity. (PickFromMenu)

After placing your order, you can see all the progress of your pizza(s), since the moment that you order, 
until you get it at your home (Not implemented yet!).

Note: All activities (except the order summary) contains a top menu shortcut icon that will lead the user to the final order (OrderSummary).




### Home Activity

Where you can choose if you want to "Project Your Pizza", "Pick From Menu", open final Order Summary, or even track
your pizza delivery progress (which is not implemented yet):

![screenshot_20170817-110832](https://user-images.githubusercontent.com/22459366/29437860-318beedc-83ab-11e7-954b-4a942ca43be9.png)



### Project Your Pizza

In this activity, the user is able to select the dough he desires (3 options), and which ingredients he wants.

![screenshot_20170817-233853](https://user-images.githubusercontent.com/22459366/29438012-f7abca7e-83ab-11e7-8022-2ee9eff3cd58.png)



### Pick From Menu 

If the user isn't sure about creating his/her own pizza from scratch, the app has a menu with 5 suggestions (could be more
or less, depending on the pizza restaurant owner opinion).

![screenshot_20170817-233935](https://user-images.githubusercontent.com/22459366/29438038-18838d86-83ac-11e7-8697-2bf9c88b72cf.png)



### Pick From Menu (Expanded Group)

When a group(pizza) is pressed/selected, the user sees the description of the ingredients in the selected pizza, the 
estimated time of production and the pizza price (per unit).
The user can order how many pizzas he wants at once. 

![screenshot_20170817-233932](https://user-images.githubusercontent.com/22459366/29438056-2ee86c04-83ac-11e7-89ff-97f184ec63ba.png)



### Order Summary

Final order summary (similar to a shopping cart) where the user can see the description of his/her order.
In each item of the ListView you can look at the pizza name, - if it was created from scratch in "ProjectYourPizza",
it appears "VostraPizza" plus the number of that specific pizza, or if it was selected from menu it appears the pizza's
name - the ingredients in it, the quantity and the price.
If the list isn't empty, there will appear a toolbar in the bottom side of the screen with the total price (sum of the price
of all the pizzas).

![screenshot_20170817-233939](https://user-images.githubusercontent.com/22459366/29438057-33d9bc40-83ac-11e7-8191-25a291d0b4fa.png)

