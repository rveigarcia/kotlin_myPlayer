package com.mon.myplayer2

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun add(x: Int, y: Int): Int = x + y

    fun test() {

        var x = 20
        val y: Long = x.toLong()
    }

    abstract class  Person(name: String, val age: Int){
        /*   val name: String = name
         val age: Int = age

       init{
             this.name = name
             this.age = age
         }*/
        var name = name
        get() = "Name: $field" // modificamos el getter
        set(value) {
            field = value
        }
    }

    class Developer1: Person {
        constructor(name: String) : super(name, 20)
        constructor(age: Int) : super("Jhon",age)
    }

    class Developer2(name: String) : Person(name, 20) {
    }

    fun  test2(){

     //   val p = Person("Jhon", 30) // casca porque Person es abstacta
        val d = Developer1("Tom")
        val name = d.name // getter con Valor Name: Tom
    }
interface Logger {

    val tag: String
        get() = javaClass.simpleName // esta es la forma de añadir propiedades a una interface

    fun logD(message: String){
        Log.d(tag, message)
    }

    fun logE(message: String){
        Log.e(tag, message)
    }
}

fun test3(view: View){

    val result = when (view){
        is TextView -> view.text.toString()
        is ViewGroup -> view.childCount.toString()
        else -> "Nothing found"
    }
}

fun test4(view: View){

    val result = when {
        view is TextView -> {
            view.text.toString()
        }
   //     2 > 3  -> view.childCount.toString()
        else -> "Nothing found"
    }
}

fun test5(contex: Context){

    val textView = TextView(contex).apply {
        text = "Hello"
        hint = "Goodby"
        textSize = 25f
    }
}

fun test6(){
  //  val sum: (Int, Int) -> Int = { x, y -> x + y}
  //  val mul = {x: Int, y: Int -> x * y}
 //   val res = doOp(2, 3, sum) //6
 //   val res = doOp(2, 3, mul) //6
/*
    val res = doOp(2, 3) { x, y ->
        x - y
    } //-1   */
    val res = doOp(2,3, ::sum) //5
}

fun doOp (x: Int, y : Int, op: (Int, Int) ->Int) = op(x, y)

fun sum(x: Int, y: Int): Int = x + y

fun test7(){
    val lazyVal by lazy { 20 }
    lazyVal.toString()  // el código que lleva la lambda no se ejecuta hasta este punto
}

fun test8(){
    // si la lista se crea con val  será inmutable al nombrarla con listOf
    val listOfAny: List<Any> = listOf(2, 4, 5, 6, "String")
    val listOfInt = listOf(2, 4, 5, 6)

    val result = listOfInt.filter { it % 2 == 0}  // filtrar solo por valores enteros
        .map { it.toString() }  // los pares los pasamos a String
        .sorted() //los ordenamos

    val mutable = mutableListOf(3, 2, 5)  //esta lista se podrá modificar
    val listOfInt2 = emptyList<Int>()  // inicializamos una lista vacvía de enteros
    val newList = listOfInt2 + 3  // añadir el elemento 3 a lista
    val newlist2 = listOfInt + newList  // creamos una lista con los elemetos de otras dos

    val mutableList: MutableList<Int> = mutableListOf()  // lista de Int vacía

    val set = setOf<Int>(3, 4, 5, 6, 3)  // este tipo de lista eliminaría el 3, no guarda elementos duplicados
    val map = mapOf("a" to 1, Pair("b",2 ))   // guarda lista de pares, clave valor
    val map1 = mapOf("a" to 1, "b" to 2)    // map igual a map2 pero con funcionmes infix

    for (i in 0 until 10) {  // funciones infix until sin punto

    }
    0 until2 (10) // otra funcion infix

    for((key, value) in map){ //recorrer un map

    }

    // las secuencias lo que hacen es retrasar lo max pòsible la ejecución de las operaciones de tal forma que solo necesitemos hacer una operación por cada uno de los elementos de la lista
    val result2 = listOfInt
        .asSequence()      // convertinmmos la lista en una secuancia
        .filter { it % 2 == 0}
        .map { it.toString() }
        .toList()   // y convertimos la secuancia denuevo en una lista
}

infix fun Int.until2 (x: Int){

}

fun <T>T.apply2(body: T.() -> Unit): T{
    this.body()
    return this
}

fun <T, U> T.run2(body: T.() -> U): U{
    return this.body()
}

fun <T, U> T.let2(body: (T) -> U): U{
    return body(this)
}

fun <T, U> with2(receiver: T, body: T.() -> U): U{
    return receiver.body()
}

fun <T>T.also2(body: (T) -> Unit): T{
    body(this)
    return this
}

// Formas de declarar objetos
object  MyObject{
    val x = 20
    val y = "Hello"
}

fun test9(){
    MyObject.x
    MyObject.y
}

fun test10() {

    val obj = object {
        val x = 20
        val y = "Hello"
    }
    obj.x
    obj.y
}

// empleamos object para crear una clase anónima
fun test11(view: View){
    view.setOnClickListener(object : View.OnClickListener{
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    })
    MainActivity.EXTRA_ID
}

//Companion object, es un objeto que todas las instancias de una misma clases comparten, es lo equivalente
// a los estáticos en java, si se modifica es estado de una propiedad, se hace para todas las instancias
// El MainActivity.EXTRA_ID de arriba, accedemos al companio pnjec definido en el MainActiviti, sele podrían implemetar interfaces ...

//propiedades de extensión y empleo de sobrecarga de operadores
fun test12(viewGroup: ViewGroup){
    viewGroup.size
    viewGroup[0]  //funciones de operador
}

val ViewGroup.size: Int
    get() = childCount

operator fun ViewGroup.get(index: Int): View = getChildAt(index)

//corrutinas, para que se ejcute hay que dar le un scope

fun test13(viewGroup: ViewGroup) {

    GlobalScope.launch(Dispatchers.Main){
        val result = withContext(Dispatchers.IO){ heavyTask() }  // modificamos el hilo de ejecución, el icono al lado del nun de linea indica que es una llamada a una función en suspensión
        print(result)
    }
}

fun heavyTask(): String = "Hello"

fun test14(){
    val x: Int? = null
    val l: Long = if (x != null)x.toLong() else 0
    val l2: Long? = x?.toLong()
    val l3: Long = x?.toLong() ?:0   // esto es lo más recomendable dar un valor por defecto antes de enviar null
}

sealed class Op{

    class Add(val value: Int) : Op()
    class Sub(val value: Int) : Op()
    class Mul(val value: Int) : Op()
    object Inc : Op()
}
fun doOp (x: Int, op: Op): Int = when (op) {
    is Op.Add -> x + op.value
    is Op.Sub -> x - op.value
    is Op.Mul -> x * op.value
    Op.Inc -> x + 1
}

fun test15() {
    val sequence = sequenceOf("One", "Two", "Three","Four")

    val list = listOf("One", "Two", "Three","Four")
    val listAsSeq = list.asSequence()// Para convertir una lista en una secuencia. Util para hacer operaciones cuando las colecciones son muy grandes

    val strNumberSize = list    // como crear una sequencia apartir de una lista sullos elementos tengan una longitud mayor de 3
        .asSequence()
        .filter { it.length > 3}
        .map { it.length}
        .toList()               // finalmente los convertimos la secuencia en una lista



    val oddNumber = generateSequence(1) {it + 2}    //  Crear una secuencia de números impares apartir de una semilla y una función.
        .map { it.toString() }                           // conertimos a a String
        .takeWhile { it.length < 3 }                     // cara que la creación de elementos no supere 99
        .toList()                                        // finalmente los convertimos la secuencia en una lista

    // generar un bloque de secuencias
    val randomNumbers =  sequence {
        yield(3)                                   // añadimos valores a las sequencia con funciones yield por ejemplo añadimos un 3
        yieldAll(listOf(4, 5, 6, 8, 25))                // añadimos un cojunto de elementos
        yieldAll(generateSequence (2) { it * it})  //  añadimos a la secuencia otra secuencia que ingrese valores cuadrados
    }

    // operaciones que necitan una gran cantidad de estado, orted(ordenación, distint...



    }

    // acontinución contrucción de Flows
    fun test16()  {

        val flow1 = listOf(1,2,3,4).asFlow()    // sinos viene la lista
        val flow2 = flowOf(1,2,3,4)    // o crear el flow de cero

        // el mas habitaul es el siguiente se llamam bloque flow

        val flow3 = emitFlow()
            .filter { it % 2 == 0}  //filtramos los pares
            .map { "Value is $it" } // convertimos a string

        val flow4 = emitFlow()
            .transform {  value ->      //  transform nos permite hacer transformaciones ttodo lo complejas que necesitemos
                emit(value)             // emitimos el propio valor
                emit(value *value)  // y el valor por si mismo

        }

    }

    fun emitFlow() = flow {
        for (i in (0..100)) {        // de 0 hasta 3
            delay(200)      // cada 200 milisegundos
            emit(i)                 //añadimos valores con la función emit
        }
    }

    fun test17() {
    // combinar varios flows en uno
        val flow1 = flowOf(1,2,3,4)
        val flow2 = flowOf("1","2","3","4")
        val flow3  = flow1.zip(flow2) {a,b -> "$a -> $b"}   // zip devuelve para cada par de valores un nuevo valor del flow1 el valor a y del flow2 el valor b

    }

    fun main(): Unit = runBlocking {

        // colect es un operador terminal que le indica a al flow que ya hay alguien al otro lado esperando resultados será necesario estar en un texto de corrutina, por eso el main. El runBlocking es para que no acabe el ejemplo hasta que finalicemos
     //   emitFlow().collect {
      //      println(it)
       // }

       // emitFlow().first()         // Si un flow emnite un valor solo devolvería el primero

        // no podemos cambiar un contextoi dentro de un flow
        // a escepción de que le indiquemos en que contexto se debe ejecutar cada flow con flowOn. De lo contrario daría error

        emitFlow2()
            .flowOn(Dispatchers.IO)
            .catch { throwable -> println(throwable.message) }
            .collect { println(it) }
    }

    fun emitFlow2() = flow {
     //   withContext(Dispatchers.IO){
            for (i in (0..100)) {
            delay(200)
            emit(i)
            }
   //     }
    }