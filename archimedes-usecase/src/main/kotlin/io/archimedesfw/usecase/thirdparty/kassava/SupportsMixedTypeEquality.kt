/*************************************************************************
 * [Kassava](https://github.com/consoleau/kassava) looks like is not maintained anymore.
 * Also, when Bintray was deleted, you can't locate it in any public repository.
 * So, we copy here the v2.1.0 source code until deciding what to do or a better solution.
 *************************************************************************/

package io.archimedesfw.usecase.thirdparty.kassava

/**
 * Interface to enable mixed-type equality.
 * See: http://www.artima.com/lejava/articles/equality.html
 *
 * @author James Bassett (james.bassett@console.com.au)
 */
interface SupportsMixedTypeEquality {

    /**
     * Returns true if this object can be compared for equality with the other object.
     *
     * The typical implementation is to check if other is an instance of this class. This method should always be
     * overridden in subclasses, unless they are trivially different (no new fields).
     */
    fun canEqual(other: Any?): Boolean
}
