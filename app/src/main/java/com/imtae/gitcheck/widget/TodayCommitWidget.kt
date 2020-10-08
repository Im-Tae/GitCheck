package com.imtae.gitcheck.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.imtae.gitcheck.R
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.repository.ContributionRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class TodayCommitWidget: AppWidgetProvider(), KoinComponent {

    private val contribution : ContributionRepository by inject()

    private val userInfo : User by inject(named("getUserInfo"))

    private val currentDate : String by inject(named("getCurrentDate"))

    private lateinit var views: RemoteViews

    private val compositeDisposable = CompositeDisposable()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        views = RemoteViews(context.packageName, R.layout.today_commit_widget)

        for (appWidgetId in appWidgetIds) {

            views.setViewVisibility(R.id.today_commit_textView, View.INVISIBLE)
            views.setViewVisibility(R.id.today_commit_progress_bar, View.VISIBLE)

            appWidgetManager.updateAppWidget(appWidgetId, views)

            compositeDisposable.add(
                contribution.getDesiredContribution(userInfo.login, currentDate).subscribe { it ->
                    updateAppWidget(context, appWidgetManager, appWidgetId, it.count)
                }
            )
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) = compositeDisposable.dispose()

    override fun onDisabled(context: Context?) = compositeDisposable.clear()

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, commitCount: Int) {

        val intent = Intent(context, TodayCommitWidget::class.java)
            .setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))

        val pending = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        views.apply {
            setViewVisibility(R.id.today_commit_textView, View.VISIBLE)
            setViewVisibility(R.id.today_commit_progress_bar, View.INVISIBLE)
            setTextViewText(R.id.today_commit_textView, "${context.getString(R.string.main_title)} $commitCount ")
            setOnClickPendingIntent(R.id.today_commit_layout, pending)
        }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}