package com.javasampleapproach.kotlin.csv

class Kpis {
    var annee: String
    var mois: String
    var appareil: String
    var commandes: String
    var impressions: String
    var clics: String
    var couts: String
    var chiffreAffaire: String

    constructor(annee: String, mois: String, appareil: String, commandes: String, impressions: String, clics: String, couts: String, chiffreAffaire: String) {
        this.annee = annee
        this.mois = mois
        this.appareil = appareil
        this.commandes = commandes
        this.impressions = impressions
        this.clics = clics
        this.couts = couts
        this.chiffreAffaire = chiffreAffaire
    }

    override fun toString(): String {
        return "[annee=" + annee + ", mois=" + mois + ", appareil=" + appareil + ", commandes=" + commandes + ", impressions=" + impressions + ", clics=" + clics + ", couts=" + couts + ", CA=" + chiffreAffaire + "]"
    }
}