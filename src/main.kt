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
        
        fileReader = BufferedReader(FileReader("pouet.csv"))

        // Read CSV header
        fileReader.readLine()

        // Read the file line by line starting from the second line
        line = fileReader.readLine()

        while (line != null) {
            val tokens = line.split(";")
            if (tokens.size > 0) {
                val kpis2 = Kpis(
                    tokens[DONNEES_ANNEE],
                    tokens[DONNEES_MOIS],
                    tokens[DONNEES_APPAREIL],
                    tokens[DONNEES_COMMANDES],
                    tokens[DONNEES_IMPRESSION],
                    tokens[DONNEES_CLICS],
                    tokens[DONNEES_COUTS],
                    tokens[DONNEES_CHIFFRE_AFFAIRES]
                    )
                    kpis1.add(kpis2)
            }

            line = fileReader.readLine()
        }
        // Print the new customer list
        for (ligne in kpis1) {
            println(ligne)
        }

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