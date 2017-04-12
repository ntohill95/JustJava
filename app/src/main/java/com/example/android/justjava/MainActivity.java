
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.value;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */


public class MainActivity extends AppCompatActivity {

    private CheckBox isBoxChecked;
    private CheckBox isBox2Checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.isBoxChecked = (CheckBox) findViewById(R.id.notify_me_checkbox);
        this.isBox2Checked = (CheckBox) findViewById(R.id.notify_me_checkbox2);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0;

    public void submitOrder(View view) {
        boolean checkedState = isBoxChecked.isChecked();
        boolean checkedState2 = isBox2Checked.isChecked();
        EditText nameView = (EditText) findViewById(R.id.name_view);
        String nameEntered = nameView.getText().toString();
        int price = calculatePrice();
        String priceMessage = createOrderSummary(price, checkedState, checkedState2, nameEntered);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for : " + nameEntered);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    public void increment(View View) {
        if (quantity ==100){
            quantity = 100;
            displayQuantity(quantity);
            Toast.makeText(this, "You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View View) {
        if (quantity ==0){
            quantity = 0;
            displayQuantity(quantity);
            Toast.makeText(this, "You cannot order less than 0 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.no_value);
        quantityTextView.setText(Integer.toString(number));
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private int calculatePrice() {
        int price = quantity * 5;
        if (isBoxChecked.isChecked()) {
            int additionalCream = quantity*1;
            price+=additionalCream;
        }
        if (isBox2Checked.isChecked()) {
            int additionalChoc = quantity*1;
            price+=additionalChoc;
        }
        return price;
    }

    private String createOrderSummary(int price, boolean checkedState, boolean checkedState2, String nameEntered) {

        String priceMessage = getString(R.string.order_summary_name, nameEntered) ;
        priceMessage += "\n"+ getString(R.string.addcream) + "= " + checkedState;
        priceMessage += "\n" + getString(R.string.choc)+ "= " + checkedState2;
        priceMessage += "\n" + getString(R.string.quantity) + "= " + quantity;
        priceMessage += "\n" + getString(R.string.total) + "= £" + calculatePrice();
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    public void reset(View view) {
        quantity = 0;
        String newMessage = "£0";
        displayMessage(newMessage);
        displayQuantity(quantity);
        if (isBoxChecked.isChecked()) {
            isBoxChecked.setChecked(false);
        }
        if (isBox2Checked.isChecked()) {
            isBox2Checked.setChecked(false);
        }
        EditText nameView = (EditText) findViewById(R.id.name_view);
        nameView.getText().clear();
    }
}
