package io.archimedesfw.data

interface GeneratedKeys {

    val keysRows: List<Map<String, Any>>

    fun singleKey(): Any {
        check(keysRows.size == 1 && keysRows[0].size == 1) {
            "The singleKey method should only be used when a single key is returned." +
                    " The current key entry contains multiple keys: $keysRows"
        }
        return keysRows[0].values.first()
    }

    fun singleKeysRow(): Map<String, Any> {
        check(keysRows.size == 1) {
            "The singleKeysRow method should only be used when keys for a single row are returned." +
                    " The current key list contains keys for multiple rows: $keysRows"
        }
        return keysRows[0]
    }

}
