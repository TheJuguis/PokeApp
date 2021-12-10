package fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.basicnavigation.R
import com.example.basicnavigation.database.User
import com.example.basicnavigation.databinding.FragmentLeftBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

private lateinit var database: DatabaseReference

class LeftFragment : Fragment() {


    private lateinit var binding: FragmentLeftBinding
    private val leftViewModel: LeftViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLeftBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view,savedInstanceState)


        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference

        /*
        val userID = view?.findViewById<EditText>(R.id.etUserId)
        val userName = view?.findViewById<EditText>(R.id.etUserName)
        val userMail = view?.findViewById<EditText>(R.id.etUserEmail)
        val userLevel = view?.findViewById<EditText>(R.id.etUserlevel)
        val userCapt = view?.findViewById<EditText>(R.id.etUserCapturas)

        */
        val userToGet = view?.findViewById<EditText>(R.id.etUserIdToGet)

        //val btnSend = view?.findViewById<Button>(R.id.btnSet)
        val btnGet = view?.findViewById<Button>(R.id.btnGet)
        val userNameGet = view?.findViewById<TextView>(R.id.userNameGet)
        val userEmailGet = view?.findViewById<TextView>(R.id.userEmailGet)
        val userLevelGet = view?.findViewById<TextView>(R.id.userLevelGet)
        val userNumCapturasGet = view?.findViewById<TextView>(R.id.userNumCapturasGet)


        /*
        btnSend.setOnClickListener {
            writeNewUser(userID.text.toString(), userName.text.toString(), userMail.text.toString(), userLevel.text.toString(),userCapt.text.toString() )
            userID.text.clear()
            userName.text.clear()
            userMail.text.clear()
            userLevel.text.clear()
            userCapt.text.clear()
        }

         */

        btnGet.setOnClickListener {
            getUser(userToGet.text.toString(), userNameGet, userEmailGet,userLevelGet,userNumCapturasGet)
            userToGet.text.clear()
        }


    }

    fun getUser(userId: String, userNameget: TextView, userEmailget: TextView, userLevelGet: TextView,userNumCapturasGet: TextView){
        database.child("usuarios").child(userId).get().addOnSuccessListener { record ->
            val json = JSONObject(record.value.toString())
            Log.d("ValoresFirebase", "got value ${record.value}")
            userNameget.setText("Nombre del usuario: ${json.getString("nombre")}")
            userEmailget.setText("Nickname del usuario: ${json.getString("nickname")}")
            userLevelGet.setText("Nivel de entrenador: ${json.getString("nivel")}")
            userNumCapturasGet.setText("Capturas Totales: ${json.getString("total")}")


        }
    }

    fun writeNewUser(userId: String, name: String, nickname: String, level: String, total: String){
        val user = User(name, nickname, level,total)
        database.child("usuarios").child(userId).setValue(user)

    }

}


class User(name: String, nickname: String, level: String, total: String){
    val nombre = name
    val nickname = nickname
    val nivel = level
    val total = total


}