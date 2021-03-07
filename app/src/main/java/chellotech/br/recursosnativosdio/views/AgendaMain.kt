package chellotech.br.recursosnativosdio.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import chellotech.br.recursosnativosdio.R
import kotlinx.android.synthetic.main.activity_agenda_main.*
import kotlinx.android.synthetic.main.activity_main.*

class AgendaMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda_main)

        title = "Agenda"

        evento.setOnClickListener { chamarEvento() }

    }

    private fun chamarEvento() {

        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CONTENT_URI)
            .putExtra(TITLE, "Bootcamp Everis")
            .putExtra(EVENT_LOCATION,"On-line")
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60*60*1000))

        startActivity(intent)

    }
}