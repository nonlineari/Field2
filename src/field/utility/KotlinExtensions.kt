package field.utility

import field.graphics.Bracketable
import field.linalg.Vec2
import field.linalg.Vec3
import field.linalg.Vec4
import fieldlinker.AsMap
import kotlin.Pair
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Grab-bag of Kotlin Extension functions for doing things in the Field codebase
 */

operator fun <T> AsMap.plusAssign(pair: Pair<Dict.Prop<T>, T>) {
    this.asMap_set(pair.first.name, pair.second)
}


operator fun <T> AsMap.plusAssign(pair: Dict.Prop<Boolean>) {
    this.asMap_set(pair.name, true)
}


inline fun <R, T : Bracketable> T.use(b: (T) -> R): R {
    this.open()
    var r: R
    try {
        r = b(this)
    } finally {
        this.close()
    }
    return r
}

/**
 * Threadlocal delegate, missing from Kotlin Stdlib?
 */

class PerThread<T>(val initial: () -> T) : ReadWriteProperty<Any, T> {

    val t: ThreadLocal<T> = ThreadLocal.withInitial { initial() }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return t.get()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        t.set(value)
    }

}


/**
 * Linalg work below
 */


operator fun Vec2.plus(other: Vec2): Vec2
        = Vec2(this.x + other.x, this.y + other.y)

operator fun Vec3.plus(other: Vec3): Vec3
        = Vec3(this.x + other.x, this.y + other.y, this.z + other.z)

operator fun Vec4.plus(other: Vec4): Vec4
        = Vec4(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w)

operator fun Vec2.minus(other: Vec2): Vec2
        = Vec2(this.x - other.x, this.y - other.y)

operator fun Vec3.minus(other: Vec3): Vec3
        = Vec3(this.x - other.x, this.y - other.y, this.z - other.z)

operator fun Vec4.minus(other: Vec4): Vec4
        = Vec4(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w)

operator fun Vec2.times(other: Number): Vec2
        = Vec2(this.x * other.toDouble(), this.y * other.toDouble())

operator fun Vec3.times(other: Number): Vec3
        = Vec3(this.x * other.toDouble(), this.y * other.toDouble(), this.z * other.toDouble())

operator fun Vec2.times(other: Vec2): Vec2
        = Vec2(this.x * other.x, this.y * other.y)

operator fun Vec3.times(other: Double): Vec3
        = Vec3(this.x * other, this.y * other, this.z * other)

operator fun Vec3.times(other: Vec3): Vec3
        = Vec3(this.x * other.x, this.y * other.y, this.z * other.z)

operator fun Vec4.times(other: Double): Vec4
        = Vec4(this.x * other, this.y * other, this.z * other, this.w * other)

operator fun Vec4.times(other: Number): Vec4
        = Vec4(this.x * other.toDouble(), this.y * other.toDouble(), this.z * other.toDouble(), this.w * other.toDouble())


operator fun Vec2.remAssign(to: Vec2) {
    this.set(to)
};
operator fun Vec3.remAssign(to: Vec3) {
    this.set(to)
};
operator fun Vec4.remAssign(to: Vec4) {
    this.set(to)
};

operator fun Vec2.compareTo(n: Number): Int = this.length().compareTo(n.toDouble())
operator fun Vec3.compareTo(n: Number): Int = this.length().compareTo(n.toDouble())
operator fun Vec4.compareTo(n: Number): Int = this.length().compareTo(n.toDouble())


operator fun Vec2.timesAssign(other: Double) {
    this.mul(other)
}

operator fun Vec3.timesAssign(other: Double) {
    this.mul(other)
}

operator fun Vec4.timesAssign(other: Double) {
    this.mul(other)
}

fun Vec2(x: Number = 0, y: Number = 0) = Vec2().set(x.toDouble(), y.toDouble())
fun Vec3(x: Number = 0, y: Number = 0, z: Number = 0) = Vec3().set(x.toDouble(), y.toDouble(), z.toDouble())
fun Vec4(x: Number = 0, y: Number = 0, z: Number = 0, w: Number = 0) = Vec4().set(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())


class xyDelegate : ReadWriteProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.x, thisRef.y)
    }

    override fun setValue(thisRef: Vec3, property: KProperty<*>, value: Vec2) {
        thisRef.x = value.x
        thisRef.y = value.y
    }

}

var Vec3.xy: Vec2 by xyDelegate()

class yxDelegate : ReadWriteProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.y, thisRef.x)
    }

    override fun setValue(thisRef: Vec3, property: KProperty<*>, value: Vec2) {
        thisRef.y = value.x
        thisRef.x = value.y
    }

}

var Vec3.yx: Vec2 by yxDelegate()

class zyDelegate : ReadWriteProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.z, thisRef.y)
    }

    override fun setValue(thisRef: Vec3, property: KProperty<*>, value: Vec2) {
        thisRef.z = value.x
        thisRef.y = value.y
    }

}

var Vec3.zy: Vec2 by zyDelegate()

class zxDelegate : ReadWriteProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.z, thisRef.x)
    }

    override fun setValue(thisRef: Vec3, property: KProperty<*>, value: Vec2) {
        thisRef.z = value.x
        thisRef.x = value.y
    }

}

var Vec3.zx: Vec2 by zxDelegate()

class xzDelegate : ReadWriteProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.x, thisRef.z)
    }

    override fun setValue(thisRef: Vec3, property: KProperty<*>, value: Vec2) {
        thisRef.x = value.x
        thisRef.z = value.y
    }

}

var Vec3.xz: Vec2 by xzDelegate()

class xxDelegate : ReadOnlyProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.x, thisRef.x)
    }

}

val Vec3.xx: Vec2 by xxDelegate()

class yyDelegate : ReadOnlyProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.y, thisRef.y)
    }

}

val Vec3.yy: Vec2 by yyDelegate()

class zzDelegate : ReadOnlyProperty<Vec3, Vec2> {
    override fun getValue(thisRef: Vec3, property: KProperty<*>): Vec2 {
        return Vec2(thisRef.z, thisRef.z)
    }

}

val Vec3.zz: Vec2 by zzDelegate()

val Vec4.xyz: Vec3 by xyzDelegate()

class xyzDelegate : ReadOnlyProperty<Vec4, Vec3> {
    override fun getValue(thisRef: Vec4, property: KProperty<*>): Vec3 {
        return Vec3(thisRef.x, thisRef.y, thisRef.z)
    }

}
