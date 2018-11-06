package me.paul.luminescence.geometry

import me.paul.luminescence.shading.{Material, RayHit}

class Sphere(val center: Point3D, val radius: Double, val material: Material) extends Geometry {

    def intersects(ray: Ray3D): Boolean = {
        val a: Double = ray.direction dot ray.direction
        val b: Double = (ray.direction * 2) dot (ray.start - center)
        val c: Double = ((ray.start - center) dot (ray.start - center)) - (radius * radius)

        val preDiscriminant: Double = (b * b) - (4 * a * c)

        preDiscriminant >= 0
    }

    def intersections(ray: Ray3D): List[RayHit] = {
        val a: Double = ray.direction dot ray.direction
        val b: Double = (ray.direction * 2) dot (ray.start - center)
        val c: Double = ((ray.start - center) dot (ray.start - center)) - (radius * radius)

        val preDiscriminant: Double = (b * b) - (4 * a * c)

        if (preDiscriminant < 0) {
            List.empty
        } else {
            val discriminant: Double = math.sqrt(preDiscriminant)
            if (preDiscriminant == 0) {

                val solution: Double = (-b + discriminant) / (2 * a)

                List(RayHit(ray, this, solution))

            } else {

                val solutionA: Double = (-b + discriminant) / (2 * a)
                val solutionB: Double = (-b - discriminant) / (2 * a)

                List(RayHit(ray, this, solutionA), RayHit(ray, this, solutionB))
            }
        }

    }

    def normalAt(point: Point3D): Vector3D = {
        (center to point).normalize
    }

    def diameter: Double = {
        radius * 2
    }

    def volume: Double = {
        (4.0 / 3.0) * math.Pi * (radius * radius * radius)
    }

    def surfaceArea: Double = {
        4.0 * math.Pi * (radius * radius)
    }

    def circumference: Double = {
        2.0 * math.Pi * radius
    }

    override def toString: String = {
        s"Sphere(c = $center, r = $radius)"
    }

    override def equals(obj: scala.Any): Boolean = {
        obj match {
            case s: Sphere =>
                center == s.center && radius == s.radius
            case _ =>
                false
        }
    }

}

object Sphere {

    def apply(c: Point3D, r: Double, m: Material): Sphere = new Sphere(c, r, m)

}
