package io.archimedesfw.data.tx.action

class MutableBulkActionSummary : BulkActionSummary() {

    fun addSuccess(): MutableBulkActionSummary {
        _successCount++
        return this
    }

    fun addError(index: Int, id: String, e: Exception): MutableBulkActionSummary {
        _errors.add(ErrorDetail(index, id, e))
        return this
    }

}
