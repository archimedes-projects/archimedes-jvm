package io.archimedesfw.data

interface GeneratedKeys {

    val rows: List<Map<String, Any>>

    fun singleKey(): Any {
        check(rows.size == 1 && rows[0].size == 1) {
            "The singleKey method should only be used when just a single key is returned by the database." +
                    " The current rows entries contains multiple keys: $rows"
        }
        return rows[0].values.first()
    }

    fun singleRow(): Map<String, Any> {
        check(rows.size == 1) {
            "The singleRow method should only be used when keys for a single row are returned by the database." +
                    " The current rows entries contains keys for multiple rows: $rows"
        }
        return rows[0]
    }

}
