package com.fabiolopz.moviesfeed.movies

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabiolopz.moviesfeed.R

class ListAdapter(
    private val list: List<ViewModel>,
    private val resource: Int,
    private val activity: Activity
):RecyclerView.Adapter<ListAdapter.ListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view: View = LayoutInflater
            .from(parent.context).inflate(resource, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.itemName?.text = list[position].name
        holder.countryName?.text = list[position].country
        /*val picture: Picture = pictures[position]
        holder.usernameCard?.text = picture.userName
        holder.timeCardCard?.text = picture.time
        holder.likeNumberCard?.text = picture.like_number
        Picasso.get().load(picture.picture).into(holder.pictureCard)
        holder.pictureCard?.setOnClickListener {
            val intent = Intent(activity, PictureDetailActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val explode = Explode()
                explode.duration = 1000
                activity.window.exitTransition = explode
                activity.startActivity(
                    intent, ActivityOptions
                        .makeSceneTransitionAnimation(
                            activity,
                            it,
                            activity.getString(R.string.transitionname_picture)
                        ).toBundle()
                )
            } else {
                activity.startActivity(intent)
            }

        }*/
    }

    class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName:TextView? = itemView.findViewById(R.id.text_view_fragment_name)
        val countryName:TextView? = itemView.findViewById(R.id.text_view_fragment_country)
    }
}