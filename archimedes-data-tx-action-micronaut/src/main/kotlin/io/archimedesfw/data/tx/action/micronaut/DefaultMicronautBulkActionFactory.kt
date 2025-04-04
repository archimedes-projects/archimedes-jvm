package io.archimedesfw.data.tx.action.micronaut

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.data.tx.action.BulkActionFactory
import io.archimedesfw.data.tx.action.DefaultBulkActionFactory
import jakarta.inject.Singleton

@Singleton
public class DefaultMicronautBulkActionFactory(
    transactional: Transactional
) : BulkActionFactory by DefaultBulkActionFactory(transactional)
