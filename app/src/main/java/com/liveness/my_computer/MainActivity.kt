package com.liveness.my_computer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MyAdapter.KeyClickListener {
    private lateinit var recyclerView: RecyclerView
    private var resultTextView: TextView? = null
    private var operationTextView: TextView? = null
    private var currentInput = "0"
    private var numbersList = mutableListOf("")
    private var operation = ""
    private var operand1: Double? = null
    private lateinit var adapter: MyAdapter
    private var currentInput2 = ""
    private var operation2 = ""
    private var operand: Double? = null
    private var operation3 = ""
    private var op1 = ""
    private var op2 : Double? = null
    private var op3 = ""
    private var op4 : Double? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.rv_list)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        resultTextView = findViewById(R.id.result)
        operationTextView = findViewById(R.id.txtOperation)

        // Dữ liệu mẫu cho RecyclerView
        val dataList = listOf(
            data("C"),
            data("%"),
            data("Del"),
            data("/"),
            data("7"),
            data("8"),
            data("9"),
            data("*"),
            data("4"),
            data("5"),
            data("6"),
            data("-"),
            data("1"),
            data("2"),
            data("3"),
            data("+"),
            data(""),
            data("0"),
            data("."),
            data("="),
        )
        // Khởi tạo adapter với dữ liệu
        adapter = MyAdapter(dataList, this)
        recyclerView.adapter = adapter
    }

    override fun onKeyClick(item: data) {
        handleButtonClick(item.number)
    }

    private fun handleButtonClick(buttonText: String) {
        when (buttonText) {
            "=" -> calculateResult2()
            in "0".."9", "." -> appendToCurrentInput(buttonText)
            in listOf("+", "-", "*", "/") -> setOperation(buttonText)
            "C" -> deleteAll()
            "Del" -> delete()
            "%" -> rad(buttonText)
        }
    }

    private fun appendToCurrentInput(text: String) {
        when (currentInput) {
            "null" -> {
                currentInput = ""
                currentInput += text
                numbersList.add(text)
                calculateResult()
                updateDisplay()
            }

            "" -> {
                if ((operation2=="*" || operation2 == "/" ) && operand != null ){
                    operation2 = op1
                    operand1 = op2
                    currentInput2 = op3
                }
                currentInput = currentInput2
                operation = operation2
                currentInput += text
                numbersList.add(text)
                val x = currentInput2.toDoubleOrNull()
                if (x != 0.0) {
                    when (operation) {
                        "+" -> operand1 = operand1!! - x!!
                        "-" -> operand1 = operand1!! + x!!
                        "*" -> operand1 = operand1!! / x!!
                        "/" -> operand1 = operand1!! * x!!
                    }
                }
                calculateResult()
                updateDisplay()

            }
            "%" -> {
                currentInput += text
                numbersList.add(text)
                currentInput = (currentInput2.toDoubleOrNull() !! * 0.01).toString()
            }


            else -> {
                currentInput += text
                numbersList.add(text)
                updateDisplay()
            }
        }

    }

    private fun setOperation(op: String) {
        if (currentInput != "") {
            operand1 = currentInput.toDoubleOrNull()
            if (operand1 != null) {
                operation = op
                numbersList.add(operation)
                currentInput = "null"
            } else {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        } else {
            when (op) {
                "*" -> {
                    when (operation2) {
                        "+" -> {
                            operand = operand1!! - currentInput2.toDoubleOrNull()!!
                            operation3 = operation2
                            operand1 = currentInput2.toDoubleOrNull()
                        }

                        "-" -> {
                            operand = operand1!! + currentInput2.toDoubleOrNull()!!
                            operation3 = operation2
                            operand1 = currentInput2.toDoubleOrNull()
                        }
                    }
                }

                "/" -> {

                    when (operation2) {
                        "+" -> {
                            operand = operand1!! - currentInput2.toDoubleOrNull()!!
                            operation3 = operation2
                            operand1 = currentInput2.toDoubleOrNull()
                        }

                        "-" -> {
                            operand = operand1!! + currentInput2.toDoubleOrNull()!!
                            operation3 = operation2
                            operand1 = currentInput2.toDoubleOrNull()

                        }
                    }
                }
            }
            operation = op
            numbersList.add(operation)
            currentInput = "null"

        }
        updateDisplay()
    }

    private fun calculateResult() {
        val operand2 = currentInput.toDoubleOrNull()
        currentInput2 = currentInput
        operation2 = operation
        if (operand1 != null && operand2 != null && operation.isNotEmpty()) {
            val result = when (operation) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> operand1!! / operand2
                else -> 0.0
            }
            resultTextView!!.text = result.toString()
            operation = ""
            operand1 = result
            op4 = result
            currentInput = ""
            if(operand!=null) {
                when (operation2) {
                    "*" -> ratio()
                    "/" -> ratio()
                }
            }

        }
    }
    @SuppressLint("SetTextI18n")
    private fun updateDisplay() {
        // Tạo chuỗi từ tất cả các phần tử trong numbersList
        val displayText = numbersList.joinToString(separator = "")
        // Hiển thị chuỗi trong operationTextView
        operationTextView?.text = displayText
    }

    private fun deleteAll() {
        currentInput = "0"
        operand1 = null
        operation = ""
        operand = null
        resultTextView!!.text = ""
        operationTextView!!.text = ""
        numbersList.clear()
        updateDisplay()
    }

    private fun delete() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
            numbersList.removeAt(numbersList.size - 1)
            updateDisplay()
        }
    }
    private fun ratio() {
            op1 = operation2
            op2 = operand1
            op3 = currentInput2
            currentInput = operand1.toString()
            operand1 = operand
            operation = operation3
            calculateResult ()
            operation2 = op1

    }
    private fun calculateResult2() {
        numbersList.clear()
        operand1 = op4
        operation2 = ""
        currentInput2 = ""
        operand = null
        operationTextView!!.text = ""
    }
    private fun rad(text : String){
        numbersList.add(text)
        currentInput = "0.01"
        operation = "*"
        calculateResult()
        updateDisplay()
    }
}



