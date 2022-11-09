package com.example.periodic_table_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cell(
    var AtomicNumber: String,
    var Symbol: String,
    var Name: String,
    var AtomicMass: String,
    var CPKHexColor: String,
    var ElectronConfiguration: String,
    var Electronegativity: String,
    var AtomicRadius: String,
    var IonizationEnergy: String,
    var ElectronAffinity: String,
    var OxidationStates: String,
    var StandardState: String,
    var MeltingPoint: String,
    var BoilingPoint: String,
    var Density: String,
    var GroupBlock: String,
    var YearDiscovered: String
):Parcelable
