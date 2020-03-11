package com.swetajain.coroutineasync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var counterTask: CounterTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart.setOnClickListener {
            val n = etNum.text.toString().toInt()
            counterTask = CounterTask()
            counterTask.execute(n)
        }

        btnStop.setOnClickListener {
            counterTask.cancel(true)
        }
    }

    inner class CounterTask:CoroutineAsyncTask<Int,Int,String>(){
        override fun doInBackground(vararg params: Int?): String {
            var count = params[0]?:0
            while (count >= 0){
                if (isCancelled){
                    return "Sorry!"
                }
                Thread.sleep(1000)
                publishProgress(count--)
            }
            return "Done!"
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val progress = values[0]?: 0
            status_text.text = progress.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            status_text.text = result
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
            status_text.text = result
        }
    }
}
