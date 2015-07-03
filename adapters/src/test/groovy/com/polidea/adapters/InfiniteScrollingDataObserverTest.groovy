package com.polidea.adapters

import spock.lang.Specification
import spock.lang.Unroll

class InfiniteScrollingDataObserverTest extends Specification {

    def delegateMock = Mock(InfiniteScrollingDataObserver.Delegate)

    def providerMock = Mock(InfiniteScrollingDataObserver.Provider)

    def observer = new InfiniteScrollingDataObserver(delegateMock, providerMock)

    @Unroll
    def "on item range change (positionStart: #positionStart, itemCount: #itemCount, infiniteScrollingPosition: #infiniteScrollingPosition) should call notifyItemChanged #notifyItemChangedCount times"() {
        setup:
        providerMock.getInfiniteScrollingPosition() >> infiniteScrollingPosition

        when:
        observer.onItemRangeChanged(positionStart, itemCount)

        then:
        notifyItemChangedCount * delegateMock.refreshInfiniteScrollingItem()

        where:
        positionStart | itemCount | infiniteScrollingPosition || notifyItemChangedCount
        10            | 2         | 10                        || 0
        10            | 2         | 11                        || 0
        10            | 2         | 12                        || 1
        10            | 1         | 10                        || 0
        10            | 1         | 11                        || 1
        10            | 1         | 9                         || 1

    }
}
