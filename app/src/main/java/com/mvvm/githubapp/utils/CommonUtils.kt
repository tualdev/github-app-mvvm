package com.mvvm.githubapp.utils

import android.content.Context
import com.mvvm.githubapp.GithubApp
import com.mvvm.githubapp.R
import com.mvvm.githubapp.constant.AppConstant
import org.jetbrains.anko.browse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object CommonUtils {

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT


    fun getDateStr(date: Date?): String {
        if (date?.toString() == null) {
            return ""
        } else if (date.toString().length < 10) {
            return date.toString()
        }
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date).substring(0, 10)
    }

    fun getNewsTimeStr(date: Date?): String {
        if (date == null) {
            return ""
        }
        val subTime = Date().time - date.time
        return when {
            subTime < MILLIS_LIMIT -> GithubApp.instance.getString(R.string.justNow)
            subTime < SECONDS_LIMIT -> (subTime / MILLIS_LIMIT).roundToInt().toString() + " " + GithubApp.instance.getString(R.string.secondAgo)
            subTime < MINUTES_LIMIT -> (subTime / SECONDS_LIMIT).roundToInt().toString() + " " + GithubApp.instance.getString(R.string.minutesAgo)
            subTime < HOURS_LIMIT -> (subTime / MINUTES_LIMIT).roundToInt().toString() + " " + GithubApp.instance.getString(R.string.hoursAgo)
            subTime < DAYS_LIMIT -> (subTime / HOURS_LIMIT).roundToInt().toString() + " " + GithubApp.instance.getString(R.string.daysAgo)
            else -> getDateStr(date)
        }
    }

    fun convertDateToLong(date: String?): Long {

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
        val parsedDate = sdf.parse(date)
        return parsedDate.time
    }

    fun getReposHtmlUrl(userName: String, reposName: String): String =
            AppConstant.GITHUB_BASE_URL + userName + "/" + reposName

    fun getIssueHtmlUrl(userName: String, reposName: String, number: String): String =
            AppConstant.GITHUB_BASE_URL + userName + "/" + reposName + "/issues/" + number

    fun getUserHtmlUrl(userName: String) =
            AppConstant.GITHUB_BASE_URL + userName

    fun getFileHtmlUrl(userName: String, reposName: String, path: String, branch: String = "master"): String =
            AppConstant.GITHUB_BASE_URL + userName + "/" + reposName + "/blob/" + branch + "/" + path

    fun getCommitHtmlUrl(userName: String, reposName: String, sha: String): String =
            AppConstant.GITHUB_BASE_URL + userName + "/" + reposName + "/commit/" + sha

    fun launchUrl(context: Context, url: String?) {
        if (url == null || url.isEmpty()) {
            return
        }

        if (url.startsWith("http")) {
            context.browse(url)
        }
    }

}