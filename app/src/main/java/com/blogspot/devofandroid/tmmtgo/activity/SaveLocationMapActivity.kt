package com.blogspot.devofandroid.tmmtgo.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.devofandroid.tmmtgo.R
import com.blogspot.devofandroid.tmmtgo.dataClass.GetData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_save_location_map.*

class SaveLocationMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_location_map)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment
        mapFragment.getMapAsync(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map.clear()
        map.uiSettings.isZoomControlsEnabled = true


        val ref = FirebaseDatabase.getInstance().reference.child("DataAll").child("Student")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {
                        val location = userSnapshot.getValue(GetData::class.java)
                        val locationLat = location?.latitude
                        val locationLong = location?.longitude

                        if (locationLat != null && locationLong != null) {
                            val latLng = LatLng(locationLat, locationLong)
                            map.addMarker(
                                MarkerOptions().position(latLng).title("Student location here")
                            )
                            val update = CameraUpdateFactory.newLatLngZoom(latLng, 10.0f)
                            map.moveCamera(update)
                        } else {
                            showToast("Oops! User location not found")
                        }


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Sorry! Unable to fetch the location")
            }

        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    private fun showToast(text: String) {
        Toast.makeText(this@SaveLocationMapActivity, text, Toast.LENGTH_LONG).show()
    }

}