package com.javasampleapproach.kotlin.csv

data class Kpis(var annee: Int,
           var mois: String,
           var appareil: String,
           var commandes: Number,
           var impressions: Int,
           var clics: Int,
           var couts: Number,
           var chiffreAffaire: Number) {

    override fun toString(): String {
        return "[annee=" + annee + ", mois=" + mois + ", appareil=" + appareil + ", commandes=" + commandes + ", impressions=" + impressions + ", clics=" + clics + ", couts=" + couts + ", CA=" + chiffreAffaire + "]"
    }
}