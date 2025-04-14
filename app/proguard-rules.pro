# navigation objects
-keep class com.dadadadev.superfinancer.feature.finances.FinancesRoute { *; }
-keep class com.dadadadev.superfinancer.feature.general.GeneralRoute { *; }

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}