package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var firstValue: Double? = null
    private var operator: String? = null
    private var isExecuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        if (firstValue == null && !tvInput?.text.contentEquals("-")) {
            tvInput?.text = (view as Button).text
            firstValue = (view).text.toString().toDouble()
        } else {
            tvInput?.append((view as Button).text)
        }
        isExecuted = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        firstValue = null
        operator = null
    }

    fun onDot(view: View) {
        if (tvInput?.text.toString().lastOrNull() == '.' || tvInput?.text == "") {
            return
        }
        tvInput?.append((view as Button).text)
    }

    fun onOperation(view: View) {
        when(val operator = (view as Button).text.toString()) {
            "=" -> {
                if (tvInput?.text.toString().toDoubleOrNull() != null) {
                    val secondValue = tvInput?.text.toString().toDouble()
                    val result: Double? = when(this.operator) {
                        "+" -> (firstValue ?: 0.0) + secondValue
                        "-" -> (firstValue ?: 0.0) - secondValue
                        "x" -> (firstValue ?: 0.0) * secondValue
                        "/" -> (firstValue ?: 0.0) / secondValue
                        else -> null
                    }
                    result?.let {
                        tvInput?.text =
                            if (it % 1 == 0.0) result.roundToInt()
                                .toString() else String.format("%.2f", result)
                    }
                    this.firstValue = null
                    this.operator = null
                    isExecuted = true
                }
            }
            "-" -> {
                if (tvInput?.text.contentEquals("") || isExecuted) {
                    tvInput?.text = "-"
                } else if (tvInput?.text.toString().toDoubleOrNull() != null) {
                    this.firstValue = tvInput?.text.toString().toDouble()
                    this.operator = operator
                    tvInput?.text = ""
                }
            }
            else -> {
                if (tvInput?.text.toString().toDoubleOrNull() != null) {
                    this.firstValue = tvInput?.text.toString().toDouble()
                    this.operator = operator
                    tvInput?.text = ""
                }
            }
        }
    }
}