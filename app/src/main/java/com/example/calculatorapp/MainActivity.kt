package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var tvDisplay: TextView? = null
    private var firstValue = Double.NaN
    private var secondValue = 0.0
    private var currentOperator: String? = null
    private var isOperatorSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tv_display)

        val btn0 = findViewById<Button>(R.id.btn_0)
        val btn1 = findViewById<Button>(R.id.btn_1)
        val btn2 = findViewById<Button>(R.id.btn_2)
        val btn3 = findViewById<Button>(R.id.btn_3)
        val btn4 = findViewById<Button>(R.id.btn_4)
        val btn5 = findViewById<Button>(R.id.btn_5)
        val btn6 = findViewById<Button>(R.id.btn_6)
        val btn7 = findViewById<Button>(R.id.btn_7)
        val btn8 = findViewById<Button>(R.id.btn_8)
        val btn9 = findViewById<Button>(R.id.btn_9)

        val btnAdd = findViewById<Button>(R.id.btn_add)
        val btnSub = findViewById<Button>(R.id.btn_sub)
        val btnMul = findViewById<Button>(R.id.btn_mul)
        val btnDiv = findViewById<Button>(R.id.btn_div)
        val btnEqual = findViewById<Button>(R.id.btn_equal)
        val btnDot = findViewById<Button>(R.id.btn_dot)

        val btnCE = findViewById<Button>(R.id.btn_ce)
        val btnC = findViewById<Button>(R.id.btn_c)
        val btnBS = findViewById<Button>(R.id.btn_bs)
        val btnNeg = findViewById<Button>(R.id.btn_neg)


        val numberListener = View.OnClickListener { v ->
            val b = v as Button
            if (isOperatorSelected) {
                tvDisplay?.text = ""
                isOperatorSelected = false
            }
            val currentText = tvDisplay?.text.toString()
            if (currentText == "0") {
                tvDisplay?.text = b.text.toString()
            } else {
                tvDisplay?.append(b.text.toString())
            }
        }

        btn0.setOnClickListener(numberListener)
        btn1.setOnClickListener(numberListener)
        btn2.setOnClickListener(numberListener)
        btn3.setOnClickListener(numberListener)
        btn4.setOnClickListener(numberListener)
        btn5.setOnClickListener(numberListener)
        btn6.setOnClickListener(numberListener)
        btn7.setOnClickListener(numberListener)
        btn8.setOnClickListener(numberListener)
        btn9.setOnClickListener(numberListener)

        btnAdd.setOnClickListener(OperatorClickListener("+"))
        btnSub.setOnClickListener(OperatorClickListener("-"))
        btnMul.setOnClickListener(OperatorClickListener("x"))
        btnDiv.setOnClickListener(OperatorClickListener("/"))

        btnDot.setOnClickListener {
            if (!tvDisplay?.text.toString().contains(".")) {
                tvDisplay?.append(".")
            }
        }

        btnNeg.setOnClickListener {
            val currentText = tvDisplay?.text.toString()
            if (currentText != "0") {
                if (currentText.startsWith("-")) {
                    tvDisplay?.text = currentText.substring(1)
                } else {
                    tvDisplay?.text = "-$currentText"
                }
            }
        }

        btnEqual.setOnClickListener {
            calculate()
            currentOperator = null
        }

        btnCE.setOnClickListener {
            val currentText = tvDisplay?.text.toString()
            if (currentText.length > 1) {
                tvDisplay?.text = currentText.substring(0, currentText.length - 1)
            } else {
                tvDisplay?.text = "0"
            }
        }

        btnC.setOnClickListener {
            tvDisplay?.text = "0"
            firstValue = Double.NaN
            secondValue = Double.NaN
            currentOperator = null
        }
    }

    private fun calculate() {
        if (!java.lang.Double.isNaN(firstValue)) {
            secondValue = tvDisplay?.text.toString().toDoubleOrNull() ?: 0.0
            when (currentOperator) {
                "+" -> firstValue += secondValue
                "-" -> firstValue -= secondValue
                "x" -> firstValue *= secondValue
                "/" -> if (secondValue != 0.0) {
                    firstValue /= secondValue
                } else {
                    tvDisplay?.text = "Error"
                    return
                }
            }
            tvDisplay?.text = firstValue.toString()
        } else {
            firstValue = tvDisplay?.text.toString().toDoubleOrNull() ?: 0.0
        }
    }

    private inner class OperatorClickListener(private val operator: String) :
        View.OnClickListener {
        override fun onClick(v: View) {
            if (!java.lang.Double.isNaN(firstValue)) {
                calculate()
            }
            currentOperator = operator
            isOperatorSelected = true
        }
    }
}
