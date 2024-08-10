package RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.project.R
import com.app.project.model.User
import com.bumptech.glide.Glide

class UserAdapter(
    private val users: MutableList<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onItemClick(user) }
    }

    override fun getItemCount(): Int = users.size

    fun addUsers(newUsers: List<User>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val emailTextView: TextView = itemView.findViewById(R.id.tv_email)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.iv_avatar)

        fun bind(user: User) {
            nameTextView.text = "${user.first_name} ${user.last_name}"
            emailTextView.text = user.email
            Glide.with(itemView.context).load(user.avatar).into(avatarImageView)
        }
    }
}
