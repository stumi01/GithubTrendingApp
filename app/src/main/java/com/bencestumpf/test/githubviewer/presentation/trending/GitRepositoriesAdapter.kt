package com.bencestumpf.test.githubviewer.presentation.trending

import android.content.Context
import android.widget.TextView
import com.bencestumpf.test.githubviewer.R
import com.bencestumpf.test.githubviewer.domain.models.GitRepository
import com.hannesdorfmann.annotatedadapter.annotation.ViewField
import com.hannesdorfmann.annotatedadapter.annotation.ViewType
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter


class GitRepositoriesAdapter(context: Context) : SupportAnnotatedAdapter(context), GitRepositoriesAdapterBinder {
    private val repositories: ArrayList<GitRepository> = ArrayList()

    @JvmField
    @ViewType(layout = R.layout.view_repository_row,
            views = [ViewField(id = R.id.repository_name, name = "name", type = TextView::class)])
    val repositoryRow: Int = 0

    override fun getItemCount(): Int = repositories.size

    override fun bindViewHolder(vh: GitRepositoriesAdapterHolders.RepositoryRowViewHolder?, position: Int) {
        vh?.let {
            val repository = repositories[position]
            it.name.text = repository.name
        }
    }

    fun setData(newData: List<GitRepository>) {
        repositories.clear()
        repositories.addAll(newData)
        notifyDataSetChanged()
    }
}