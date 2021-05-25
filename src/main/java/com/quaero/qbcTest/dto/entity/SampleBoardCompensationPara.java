package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class SampleBoardCompensationPara {

	 public int AddSampleToDiskWash;//加样到质控清洗
     public int DiskWashToDiskOuter;//质控清洗到质控外
     public int DiskWashToDiskInner;//质控清洗到质控内
     public int DiskOutToAddSample;//质控外到加样
     public int DiskInToAddSample;//质控内到加样
     public int AddSampleToTrackWash;//加样到轨道清洗
     public int TrackWashToTrack1;//轨道清洗到轨道1
     public int TrackWashToTrack2;//轨道清洗到轨道2
     public int Track1ToAddSample;//轨道1到加样
     public int Track2ToAddSample;//轨道2到加样
     public int TrackWashToCleaningLiquid;//轨道清洗到清洗液
     public int CleaningLiquidToAddSample;//清洗液到加样

}
