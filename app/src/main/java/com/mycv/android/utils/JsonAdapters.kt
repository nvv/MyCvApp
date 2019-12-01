package com.mycv.android.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.mycv.android.data.model.Contacts
import com.mycv.android.data.model.ObjectiveNotes
import com.mycv.android.data.model.Skills
import java.io.IOException

class ContactsTypeAdapter : TypeAdapter<Contacts?>() {
    override fun write(out: JsonWriter?, value: Contacts?) {
    }

    override fun read(reader: JsonReader?): Contacts? {
        try {
            val token = reader?.peek()
            if (token == JsonToken.NULL) {
                reader.nextNull()
                return null
            }
            if (token == JsonToken.BEGIN_OBJECT) {
                return Contacts(readObject(reader))
            }
        } catch (e: IOException) {
            return null
        }

        return null
    }

    @Throws(IOException::class)
    private fun readObject(reader: JsonReader): Map<String, String> {
        val map = LinkedHashMap<String, String>()
        reader.beginObject()
        while (reader.hasNext()) {
            map[reader.nextName()] = reader.nextString()
        }
        reader.endObject()
        return map
    }
}

class ObjectiveNotesTypeAdapter : TypeAdapter<ObjectiveNotes?>() {
    override fun write(out: JsonWriter?, value: ObjectiveNotes?) {
    }

    override fun read(reader: JsonReader?): ObjectiveNotes? {
        try {
            val token = reader?.peek()
            if (token == JsonToken.NULL) {
                reader.nextNull()
                return null
            }
            if (token == JsonToken.BEGIN_ARRAY) {
                return ObjectiveNotes(readArray(reader))
            }
        } catch (e: IOException) {
            return null
        }

        return null
    }

    @Throws(IOException::class)
    private fun readArray(reader: JsonReader): List<String> {
        val list = mutableListOf<String>()
        reader.beginArray()
        while (reader.hasNext()) {
            list.add(reader.nextString())
        }
        reader.endArray()
        return list
    }
}

class FullSkillsTypeAdapter : TypeAdapter<Skills?>() {
    override fun write(out: JsonWriter?, value: Skills?) {
    }

    override fun read(reader: JsonReader?): Skills? {
        try {
            val token = reader?.peek()
            if (token == JsonToken.NULL) {
                reader.nextNull()
                return null
            }
            if (token == JsonToken.BEGIN_OBJECT) {
                return Skills(readSkills(reader))
            }
        } catch (e: IOException) {
            return null
        }

        return null
    }

    @Throws(IOException::class)
    private fun readSkills(reader: JsonReader): Map<String, List<String>> {
        val skillsMap = mutableMapOf<String, List<String>>()
        reader.beginObject()
        while (reader.hasNext()) {
            val skills = mutableListOf<String>()
            skillsMap[reader.nextName()] = skills
            reader.beginArray()
            while (reader.hasNext()) {
                skills.add(reader.nextString())
            }
            reader.endArray()
        }
        reader.endObject()

        return skillsMap
    }
}