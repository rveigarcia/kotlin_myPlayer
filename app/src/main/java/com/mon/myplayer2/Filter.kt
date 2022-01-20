package com.mon.myplayer2

sealed class Filter {
    object  None : Filter()
    class ByType(val type: MediaItem.Type) : Filter()
}