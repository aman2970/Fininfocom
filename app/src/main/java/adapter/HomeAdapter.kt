package adapter

import model.Student
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fin.databinding.HomeItemBinding

class HomeAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private val studentList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun setList(students: List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }

}


class MyViewHolder(private val binding: HomeItemBinding) :RecyclerView.ViewHolder(binding.root){
    fun bind(student: Student){
        binding.studentNameTv.text = student.name
        binding.studentAgeTv.text = student.age.toString()
        binding.studentCityTv.text = student.city
    }
}
