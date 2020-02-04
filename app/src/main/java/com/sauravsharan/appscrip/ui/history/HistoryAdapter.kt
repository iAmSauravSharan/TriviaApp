package com.sauravsharan.appscrip.ui.history

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sauravsharan.appscrip.R
import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.User
import com.sauravsharan.appscrip.util.inflate
import kotlinx.android.synthetic.main.content_history.view.*

class HistoryAdapter(val users: List<User>, val questions: List<Questions>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    companion object {
        const val NOT_ATTEMPTED = "Not Attempted"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.game_time.text = user.attemptedAt.toString()
            itemView.player_name.text = user.userName
            itemView.first_question_history.text =  questions[0].questionText
            itemView.first_answer_history.text = user.attemptedAnswers?.get(1) ?: NOT_ATTEMPTED
            itemView.second_question_history.text =  questions[1].questionText
            itemView.second_answer_history.text = user.attemptedAnswers?.get(2) ?: NOT_ATTEMPTED
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.content_history))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(users[position])
}