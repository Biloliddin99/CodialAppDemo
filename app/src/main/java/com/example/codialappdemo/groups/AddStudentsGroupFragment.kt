package com.example.codialappdemo.groups

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentAddStudentsGroupBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.models.Groups
import com.example.codialappdemo.models.Students
import java.text.SimpleDateFormat
import java.util.*


class AddStudentsGroupFragment : Fragment() {

    private lateinit var myDbHelper: MyDbHelper
    private lateinit var students: Students
    private var dateState = false
    private val myCalendar: Calendar = Calendar.getInstance()
    private val binding by lazy { FragmentAddStudentsGroupBinding.inflate(layoutInflater) }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myDbHelper = MyDbHelper(binding.root.context)
        val isEdit = arguments?.getBoolean("isEdit")
        val group = arguments?.getSerializable("group") as Groups

        binding.apply {
            if (isEdit == true) {
                students = arguments?.getSerializable("student") as Students
                tvLabel.text = "Talaba o'zgartirish"
                btnSave.text = "O'zgartirish"
                edtName.setText(students.firstName)
                edtSurname.setText(students.lastName)
                edtDate.setText(students.date)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSave.setOnClickListener {
                val name = edtName.text.toString().trim()
                val lastName = edtSurname.text.toString().trim()

                if (dateState && name.isNotEmpty() && lastName.isNotEmpty()) {
                    if (isEdit == true) {
                        students.lastName = lastName
                        students.firstName = name
                        students.date = edtDate.text.toString().trim()
                        myDbHelper.editStudents(students)
                    } else {
                        students = Students(
                            name,
                            lastName,
                            edtDate.text.toString().trim(),
                            group
                        )
                        myDbHelper.addStudents(students)
                    }
                    Toast.makeText(context, "Saqlandi", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT).show()
                }
            }
        }


        val date = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
            myCalendar.set(Calendar.YEAR, i)
            myCalendar.set(Calendar.MONTH, i2)
            myCalendar.set(Calendar.DAY_OF_MONTH, i3)
            updateLabel()
            dateState = true
        }

        binding.edtDate.setOnClickListener {
            val dialogDate = DatePickerDialog(
                binding.root.context,
                R.style.MyMenuDialogTheme,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH],
            )
            dialogDate.show()
            dialogDate.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.blue))
            dialogDate.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.yellow))

        }

        binding.coordinateLayout.setOnTouchListener { p0, p1 ->
            binding.apply {
                edtName.clearFocus()
                edtSurname.clearFocus()
                edtName.clearFocus()
                edtDate.clearFocus()
            }
            true
        }

        return binding.root
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }

}