package chellotech.br.recursosnativosdio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chellotech.br.recursosnativosdio.R
import chellotech.br.recursosnativosdio.model.Contact

/**
 * Recursos Nativos Dio
 * @author Marcelo Alves
 * 04/03/2021
 */
class ContactAdapter(val contactsList: ArrayList<Contact>):RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
    ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_view,parent,false))

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
       holder.bindItem(contactsList[position])
    }

    override fun getItemCount(): Int = contactsList.size

    class ContactViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){


        fun bindItem(contact: Contact) {

            val contactName  = itemView.findViewById(R.id.contact_name) as TextView
            val contactPhone = itemView.findViewById(R.id.contact_phone) as TextView

            contactName.text = contact.name
            contactPhone.text = contact.phoneNumber

        }



    }
}