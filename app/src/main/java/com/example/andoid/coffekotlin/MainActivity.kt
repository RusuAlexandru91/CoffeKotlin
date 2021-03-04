package com.example.andoid.coffekotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var quantity: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the order
        submitOrder.setOnClickListener {
            // Getting the name
            val name: String = name.text.toString()
            // WhipeCream
            val hasWipedCream: Boolean = whipead_cream_checkbox.isChecked
            // Chocolate
            val hasChocolate: Boolean = chocolate_checkbox.isChecked
            // Calculte Price
            val price: Int = calculatePrice(hasChocolate, hasWipedCream)
            val priceMessage: String = createOrderSumary(price, hasWipedCream, hasChocolate, name)
            // Intent to send mail
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, "Kiwibv@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order")
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Send Email using:"))
        }

        // Listener for increment
        incrementButton.setOnClickListener {
            if (quantity == 100) {
                Toast.makeText(this, "You can not order more then 100 cups", Toast.LENGTH_SHORT)
                    .show()
            } else {
                quantity++
            }
            display(quantity)
        }

       // Listener for decrement
        decrementButton.setOnClickListener {
            if (quantity == 1) {
                Toast.makeText(this, "You can not order less then 1 cup", Toast.LENGTH_SHORT).show()
            } else {
                quantity--
            }
            display(quantity)
        }
    }

     //This method displays the given quantity value on the screen.
    private fun display(number: Int) {
        quantity_text_view.text = "$number"
    }

    // Calculte the price
    private fun calculatePrice(hasChocolate: Boolean, hasWippedCream: Boolean): Int {
        var basePrice = 5
        if (hasChocolate) {
            basePrice += 2
        } else if (hasWippedCream) {
            basePrice += 1
        }
        return quantity * basePrice
    }

   // Method to create order sumary
    private fun createOrderSumary(price: Int, addWippedCream: Boolean, addChocolate: Boolean, name: String): String {
        var priceMessage = "Name $name"
        priceMessage += "\n With Wipped Cream: $addWippedCream"
        priceMessage += "\n With Chocolate: $addChocolate"
        priceMessage += "\n Quantity: $quantity"
        priceMessage += "\n Total: $price"
        priceMessage += "\nThank you !"
        return priceMessage
    }
}
