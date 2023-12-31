package com.github.rossilor95.peakintervalfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PeakIntervalFinderTest {
    private static final String FILEPATH = "test_filepath";

    @Mock
    private TimeIntervalDataProcessor timeIntervalDataProcessor;

    @InjectMocks
    private PeakIntervalFinder underTest;

    @Test
    void shouldFindMultiplePeakIntervalsWhenPresentInData() throws IOException {
        // GIVEN
        List<TimeInterval> expected = List.of(
                new TimeInterval(LocalTime.of(13, 13, 0), LocalTime.of(13, 23, 55)),
                new TimeInterval(LocalTime.of(13, 58, 10), LocalTime.of(14, 2, 1))
        );
        List<IntervalEndpoint> endpoints = List.of(
                new IntervalEndpoint(LocalTime.of(13, 13, 0), EndpointType.START),
                new IntervalEndpoint(LocalTime.of(13, 23, 55), EndpointType.END),
                new IntervalEndpoint(LocalTime.of(13, 58, 10), EndpointType.START),
                new IntervalEndpoint(LocalTime.of(14, 2, 1), EndpointType.END)
        );
        given(timeIntervalDataProcessor.processDataFile(FILEPATH)).willReturn(endpoints);

        // WHEN
        List<TimeInterval> actual = underTest.findPeakIntervals(FILEPATH);

        // THEN
        then(timeIntervalDataProcessor).should().processDataFile(FILEPATH);
        assertEquals(expected, actual);
    }
}
