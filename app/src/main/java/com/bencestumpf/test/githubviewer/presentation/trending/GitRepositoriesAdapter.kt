package com.bencestumpf.test.githubviewer.presentation.trending

import android.content.Context
import android.support.v4.util.Pair
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.bencestumpf.test.githubviewer.R
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.hannesdorfmann.annotatedadapter.annotation.ViewField
import com.hannesdorfmann.annotatedadapter.annotation.ViewType
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter


class GitRepositoriesAdapter(context: Context,
                             private val onRepositoryClick: (String, Array<android.support.v4.util.Pair<View, String>>) -> Unit,
                             private val onLoadMoreClik: () -> Unit)
    : SupportAnnotatedAdapter(context), GitRepositoriesAdapterBinder {

    private val repositories: ArrayList<GitRepository> = ArrayList()

    @JvmField
    @ViewType(layout = R.layout.view_repository_row,
            views = [ViewField(id = R.id.repository_name, name = "name", type = TextView::class),
                ViewField(id = R.id.repository_description, name = "description", type = TextView::class),
                ViewField(id = R.id.repository_language, name = "language", type = TextView::class),
                ViewField(id = R.id.repository_stars, name = "stars", type = TextView::class)
            ])
    val repositoryRow: Int = 0

    @JvmField
    @ViewType(layout = R.layout.view_load_more_row,
            views = [ViewField(id = R.id.load_more, name = "button", type = Button::class)
            ])
    val loadMoreRow: Int = 1

    override fun getItemCount(): Int {
        return if (repositories.size > 1) {
            repositories.size + 1
        } else {
            repositories.size
        }
    }

    override fun bindViewHolder(vh: GitRepositoriesAdapterHolders.RepositoryRowViewHolder?, position: Int) {
        vh?.let {
            val repository = repositories[position]
            Log.d("STUMI", "bindViewHolder $it")
            it.name.text = repository.fullName
            it?.description.text = repository.description
            it?.language.text = repository.language
            it?.stars.text = repository.stars.toString()

            val p1 = android.support.v4.util.Pair.create(it.itemView, "background")
            val p2 = android.support.v4.util.Pair.create(it?.description, "repository_description")
            val p3 = android.support.v4.util.Pair.create(it?.language, "repository_language")
            val p4 = android.support.v4.util.Pair.create(it?.stars, "repository_stars")
            it.itemView.setOnClickListener { onRepositoryClick.invoke(repository.id, arrayOf(p1, p2, p3, p4) as Array<Pair<View, String>>) }
        }
    }

    override fun bindViewHolder(vh: GitRepositoriesAdapterHolders.LoadMoreRowViewHolder?, position: Int) {
        vh?.let {
            it.button.setOnClickListener { onLoadMoreClik.invoke() }
        }
    }

    fun setData(newData: List<GitRepository>) {
        repositories.clear()
        repositories.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (position == repositories.size) {
            return loadMoreRow
        }

        return repositoryRow
    }

    fun addData(newData: List<GitRepository>) {
        repositories.addAll(newData)
        notifyDataSetChanged()
    }
}
