package com.example.appcenter_todolist.ui.theme

//import androidx.compose.material3.Typography
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.appcenter_todolist.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontFamily_The_Jamsil = FontFamily(
    Font(R.font.the_jamsil_otf_5_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.the_jamsil_otf_4_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.the_jamsil_otf_3_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.the_jamsil_otf_2_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.the_jamsil_otf_1_thin, FontWeight.Thin, FontStyle.Normal),
)

val fontName_Inter = GoogleFont("Inter")
val fontFamily_Inter = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = fontName_Inter,
        fontProvider = provider
    )
)
object CustomTypography {
    val headerJamsil21: TextStyle = TextStyle(
        fontFamily = fontFamily_The_Jamsil,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
        lineHeight = 41.sp,
        letterSpacing = 1.sp
    )
    val bodyJamsil17: TextStyle = TextStyle(
        fontFamily = fontFamily_The_Jamsil,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
    )
    val bodyJamsil15: TextStyle = TextStyle(
        fontFamily = fontFamily_The_Jamsil,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    val headerInter20: TextStyle = TextStyle(
        fontFamily = fontFamily_Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    )
    val bodyInter20: TextStyle = TextStyle(
        fontFamily = fontFamily_Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
    )
    val bodyInterSmall16: TextStyle = TextStyle(
        fontFamily = fontFamily_Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )
    val textFieldInter15: TextStyle = TextStyle(
        fontFamily = fontFamily_Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    val bottomInter18: TextStyle = TextStyle(
        fontFamily = fontFamily_Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 27.sp,
    )
}


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

)