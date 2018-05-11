package com.example.admin.kalkulatorrpn

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_horizontal.*
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.text.DecimalFormat

class Horizontal : AppCompatActivity() {
    val REQUEST_CODE = 10000
    private var stack: MutableList<Double> = mutableListOf()
    private var precisionToSend: String = "0.00"
    private var defaultColor: Int = 0
    private var precision: DecimalFormat = DecimalFormat(precisionToSend)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal)

        labels_horizontal.text = "4:\n3:\n2:\n1:\n"

        val temp = intent.extras?.getString("stack")
        val groupListType = object : TypeToken<MutableList<Double>>() {}.type
        stack = Gson().fromJson<MutableList<Double>>(temp, groupListType)
        editingSlot_horizontal.setText(intent.extras.getString("editingSlot"))
        precisionToSend = intent.extras.getString("precisionToSend")
        precision = DecimalFormat(precisionToSend)
        defaultColor = intent.extras.getInt("defaultColor")
        display2.setBackgroundColor(defaultColor)
        labels_horizontal.setBackgroundColor(defaultColor)
        display2.text = intent.extras.getString("screen")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInf = menuInflater
        menuInf.inflate(R.menu.menu_group, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.subitem1 -> {
                precisionToSend = "0.0"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem2 -> {
                precisionToSend = "0.00"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem3 -> {
                precisionToSend = "0.000"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem4 -> {
                precisionToSend = "0.0000"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem5 -> {
                precisionToSend = "0.00000"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem6 -> {
                precisionToSend = "0.000000"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem7 -> {
                precisionToSend = "0.0000000"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.subitem8 -> {
                precisionToSend = "0.00000000"
                precision = DecimalFormat(precisionToSend)
                drawStackOnDisplay()
                return true
            }
            R.id.pick_color-> {
                val colorPicker = AmbilWarnaDialog(this, defaultColor, object: OnAmbilWarnaListener{
                        override fun onOk( dialog: AmbilWarnaDialog, color: Int){
                            defaultColor = color
                            display2.setBackgroundColor(defaultColor)
                            labels_horizontal.setBackgroundColor(defaultColor)
                        }
                        override fun onCancel(dialog: AmbilWarnaDialog){
                        }
                    }
                )
                colorPicker.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        val orientation = newConfig?.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            showActivity()
        }
    }

    private fun showActivity(){
        val i = Intent( this, MainActivity::class.java)
        val json = Gson().toJson(stack)
        i.putExtra("stack", json)
        i.putExtra("editingSlot", editingSlot_horizontal.text.toString())
        i.putExtra("precisionToSend", precisionToSend)
        i.putExtra("defaultColor", defaultColor)
        i.putExtra("screen", display2.text.toString())
        startActivityForResult(i, REQUEST_CODE)
        finish()
    }

    private fun drawStackOnDisplay() {
        display2.text = ""
        if( !stack.isEmpty() ){
            if(stack.size >= 4){
                for (i in (stack.size - 4)..(stack.size - 1)) {
                    display2.text = display2.text.toString().plus(precision.format(stack[i])).plus("\n")
                }
            }
            else{
                for (i in 0..(stack.size - 1)) {
                    display2.text = display2.text.toString().plus(precision.format(stack[i])).plus("\n")
                }
            }
        }
        else{
            display2.text = " "
        }
    }

    private fun clearStack(){
        stack.removeAll(stack)
    }

    fun onClickNumber( v: View){
        val button = v as Button
        editingSlot_horizontal.setText( editingSlot_horizontal.text.toString().plus(button.text.toString()) )
        drawStackOnDisplay()
    }

    fun onClickOperator(v: View){
        val button = v as Button
        when( button.text.toString())
        {
            "+" -> {
                if( stack.size >= 2) {
                    val first = stack.last()
                    stack.removeAt(stack.size - 1)
                    val second = stack.last()
                    stack.removeAt(stack.size - 1)
                    val result = first + second
                    stack.add(result)
                    drawStackOnDisplay()
                }
                else{
                    display2.context.toast("Liczba argumentów musi być większa niż 2!")
                }

            }
            "-" -> {
                if( stack.size >= 2) {
                    val first = stack.last()
                    stack.removeAt(stack.size - 1)
                    val second = stack.last()
                    stack.removeAt(stack.size - 1)
                    val result = second - first
                    stack.add(result)
                    drawStackOnDisplay()
                }
                else{
                    display2.context.toast("Liczba argumentów musi być większa niż 2!")
                }
            }
            "*" -> {
                if( stack.size >= 2) {
                    val first = stack.last()
                    stack.removeAt(stack.size - 1)
                    val second = stack.last()
                    stack.removeAt(stack.size - 1)
                    val result = first * second
                    stack.add(result)
                    drawStackOnDisplay()
                }
                else{
                    display2.context.toast("Liczba argumentów musi być większa niż 2!")
                }
            }
            "/" -> {
                if( stack.size >= 2 ) {
                    if( stack[stack.size - 2] != 0.0){
                        val first = stack.last()
                        stack.removeAt(stack.size - 1)
                        val second = stack.last()
                        stack.removeAt(stack.size - 1)
                        val result = second / first
                        stack.add(result)
                        drawStackOnDisplay()
                    }
                    else{
                        display2.context.toast("Nie można dzielić przez 0 cholero!")
                    }
                }
                else{
                    display2.context.toast("Liczba argumentów musi być większa niż 2!")
                }
            }
            "SQRT" -> {
                if( stack.size >= 1 ) {
                    if( stack.last() >= 0.0 ){
                        val doSqrt = stack.last()
                        stack.removeAt(stack.size - 1)
                        val result = Math.sqrt(doSqrt)
                        stack.add(result)
                        drawStackOnDisplay()
                    }
                    else{
                        display2.context.toast("Nie istnieje pierwiastek z liczby ujemnej!")
                    }
                }
                else{
                    display2.context.toast("Brak liczby na stosie!")
                }
            }
            "POW" -> {
                if( stack.size >= 1 ) {
                    val doPow = stack.last()
                    stack.removeAt(stack.size - 1)
                    val result = Math.pow(doPow, 2.0)
                    stack.add(result)
                    drawStackOnDisplay()
                }
                else{
                    display2.context.toast("Brak liczby na stosie!")
                }
            }
            "+/-" -> {
                if( stack.size >= 1 ) {
                    val doReverseSign = stack[stack.size - 1]
                    stack.removeAt(stack.size - 1)
                    val result = -doReverseSign
                    stack.add(result)
                    drawStackOnDisplay()
                }
                else{
                    display2.context.toast("Brak liczby na stosie!")
                }
            }
        }
    }

    fun onClickEnter( v: View){
        try {
            if ( !stack.isEmpty() && ( editingSlot_horizontal.text.toString() == "" ) ){
                stack.add(stack.last())
            }
            else{
                stack.add(editingSlot_horizontal.text.toString().toDouble())
            }
            editingSlot_horizontal.setText("")
            drawStackOnDisplay()
        } catch (e: NumberFormatException) {
            display2.context.toast("To nie jest liczba!")
        }
    }

    fun onClickSwap( v: View){
        if( stack.size >= 2){
            val first = stack.last()
            stack.removeAt(stack.size - 1)
            val second = stack.last()
            stack.removeAt(stack.size - 1)
            stack.add(first)
            stack.add(second)
            drawStackOnDisplay()
        }
    }

    fun onClickDrop( v: View){
        if( !stack.isEmpty() ){
            stack.removeAt(stack.size - 1)
            drawStackOnDisplay()
        }
    }

    fun onClickClear( v: View){
        clearStack()
        drawStackOnDisplay()
    }

    fun onClickUndo( v: View){
        if( (editingSlot_horizontal.text.toString().isNotEmpty()) ) {
            editingSlot_horizontal.setText(editingSlot_horizontal.text.toString().substring(0, editingSlot_horizontal.text.toString().length - 1))
            drawStackOnDisplay()
        }
    }

    private fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
