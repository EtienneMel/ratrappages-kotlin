package com.javasampleapproach.kotlin.csv

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.text.NumberFormat
import java.util.*

private val DONNEES_ANNEE = 0
private val DONNEES_APPAREIL = 1
private val DONNEES_COMMANDES = 2
private val DONNEES_IMPRESSION = 3
private val DONNEES_CLICS = 4
private val DONNEES_COUTS = 5
private val DONNEES_CHIFFRE_AFFAIRES = 7
private val DONNEES_MOIS = 9

fun main(args: Array<String>?) {
    var fileReader: BufferedReader? = null

    try {
        val kpis1 = ArrayList<Kpis>()
        var line: String?

        val mapAnnees= mutableMapOf<Int, MutableMap<String, Number>>()
        val mapAppareils = mutableMapOf<String, Number>()
        val mapAppareilsParMois = mutableMapOf<String, MutableMap<String, Number>>()

        fileReader = BufferedReader(FileReader("pouet.csv"))

        // Read CSV header
        fileReader.readLine()

        // Read the file line by line starting from the second line
        line = fileReader.readLine()
        while (line != null) {

            val tokens = line.split(";")
            if (tokens.size > 0) {
                val kpis2 = Kpis(
                    Integer.parseInt(tokens[DONNEES_ANNEE]),
                    tokens[DONNEES_MOIS],
                    tokens[DONNEES_APPAREIL],
                    NumberFormat.getNumberInstance(Locale.FRANCE).parse(tokens[DONNEES_COMMANDES]),
                    Integer.parseInt(tokens[DONNEES_IMPRESSION]),
                    Integer.parseInt(tokens[DONNEES_CLICS]),
                    NumberFormat.getNumberInstance(Locale.FRANCE).parse(tokens[DONNEES_COUTS]),
                    NumberFormat.getNumberInstance(Locale.FRANCE).parse(tokens[DONNEES_CHIFFRE_AFFAIRES])
                )
                kpis1.add(kpis2)
            }

            line = fileReader.readLine()
        }
        // Add it to the maps
        for (ligne in kpis1) {
            initialiserMapAnnees(mapAnnees, ligne.annee, ligne.mois, ligne.chiffreAffaire)
            initialiserMapAppareils(mapAppareils, ligne.appareil, ligne.chiffreAffaire)
            initialiserMapAppareilsParMois(mapAppareilsParMois, ligne.appareil, ligne.mois, ligne.chiffreAffaire)
        }

        // Lecture dans la console

            //Panier moyen
        println("------------ Panier moyen ------------")
        val chiffreAffaireTotal = kpis1.map { it.commandes.toFloat() }.sum()
        println("Panier moyen : " + formatFloat("%.2f", chiffreAffaireTotal / kpis1.size) + "€")

            // Cout par clic
        println("------------ Cout par clic ------------")
        val clicsAuTotal = kpis1.map { it.clics.toFloat() }.sum()
        val coutsAuTotal = kpis1.map { it.couts.toFloat() }.sum()
        println("Cout par clics : " + formatFloat("%.2f", coutsAuTotal / clicsAuTotal) + "€")

            //Taux de clic
        println("------------ Taux de clic ------------")
        val impressionsTotales = kpis1.map { it.impressions }.sum()
        val taux = formatFloat("%.2f", (clicsAuTotal.toFloat() / impressionsTotales.toFloat()) * 100)
        println("Taux de clic : $taux%")

            //ROI
        println("------------ Retour sur investissement ------------")
        val roi = formatFloat("%.2f", chiffreAffaireTotal / coutsAuTotal * 100)
        println("ROI : $roi%")

            // CA par mois par année
        println("------------ CA par mois par année ------------")

        for ((annees, moisMap) in mapAnnees) {
            for ((mois, chiffreAffaire) in moisMap) {
                println(" Le Chiffre d'affaire de $mois / $annees est de " + formatFloat("%.2f", chiffreAffaire) + "€")
            }
        }
            // CA par appareil
        println("------------ CA par appareil ------------")

        for ((appareil, chiffreAffaire) in mapAppareils) {
            println(" Le chiffre d'Affaire pour l'appareil $appareil est de : " + formatFloat("%.2f", chiffreAffaire) + "€")
        }

        //EXCEPTIONS

    } catch (e: Exception) {
        println("Reading CSV Error!")
        e.printStackTrace()
    } finally {
        try {
            fileReader!!.close()
        } catch (e: IOException) {
            println("Closing fileReader Error!")
            e.printStackTrace()
        }
    }
}

//Transformation au format float
fun formatFloat(format: String, value: Number): String {
    return format.format(value)
}

//Ajout des MAPS
fun initialiserMapAnnees(map: MutableMap<Int, MutableMap<String, Number>>, annee: Int, mois: String, valeur: Number)
{
    var listeAnnees = mutableMapOf<String, Number>()
    var chiffreAffaire: Number = 0

    if (map.contains(annee))
        listeAnnees = map[annee]!!
    if (listeAnnees.contains(mois))
        chiffreAffaire = listeAnnees[mois]!!

    listeAnnees[mois] = chiffreAffaire.toFloat() + valeur.toFloat()
    map[annee] = listeAnnees
}

fun initialiserMapAppareilsParMois(map: MutableMap<String, MutableMap<String, Number>>, appareil: String, mois: String, valeur: Number)
{
    var mapMois = mutableMapOf<String, Number>()
    var chiffreAffaire: Number = 0

    if (map.contains(appareil))
        mapMois = map[appareil]!!
    if (mapMois.contains(mois))
        chiffreAffaire = mapMois[mois]!!

    mapMois[mois] = chiffreAffaire.toFloat() + valeur.toFloat()
    map[appareil] = mapMois
}

fun initialiserMapAppareils(map: MutableMap<String, Number>, appareil: String, valeur: Number) {
    var chiffreAffaire: Number = 0

    if (map.contains(appareil))
        chiffreAffaire = map[appareil]!!

    map[appareil] = chiffreAffaire.toFloat() + valeur.toFloat()
}