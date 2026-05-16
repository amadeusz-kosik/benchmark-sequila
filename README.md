# Benchmarking Sequila project
[![Unit tests](https://github.com/amadeusz-kosik/benchmark-sequila/actions/workflows/unit_tests.yml/badge.svg)](https://github.com/amadeusz-kosik/benchmark-sequila/actions/workflows/unit_tests.yml)

This repository contains benchmarking code for 
[Sequila project](https://github.com/biodatageeks/sequila). It is a part of 
[Interwalled](https://github.com/amadeusz-kosik/interwalled/) effort and serves as 
a baseline for scaling and performance benchmarking.

## Available scenarios
Benchmarking is done on both synthetic data and real world one.

### Synthetic tests 
Tiered scenarios generated with 
    [data-generator-intervals](https://github.com/amadeusz-kosik/data-generator-intervals)
    utility. Each scenario is tested against databases of 100 000, 1 000 000 and 10 000 000 
    rows. Following combinations of _database_ and _query_ are used:
   - _databaseUniformFlat_ + _querySparse_
   - _databaseUniformFlat_ + _queryDense_
   - _databaseUniformFlat_ + _queryDummy_
   - _databaseUniformStacked_ + _querySparse_
   - _databaseUniformStacked_ + _queryDense_
   - _databaseSkewedStacked_ + _querySparse_
   - _databaseSkewedStacked_ + _queryDense_
   - _databaseSkewedStacked_ + _querySkewedDense_
   - _databaseUniformHeavyStacked_ + _querySparse_
   - _databaseUniformHeavyStacked_ + _queryDense_
   - _databaseUniformHeavyStacked_ + _querySkewedDense_

### Databio dataset
The AIList dataset, as described on 
    [polars-bio project](https://biodatageeks.org/polars-bio/supplement/#real-dataset).
    Full list of 36 combinations of dataset is used.    

## Running the project
1. Create _spark-events_ and _spark-job-data_ external volumes on docker. Allow user with uid 
   _185_ to read and write on those volumes.
```shell
docker volume create spark-events
docker volume create spark-job-data
docker run -v spark-events:/mnt/spark-events -v spark-job-data:/mnt/data -it ubuntu:latest /bin/bash
```
```shell
# In ubuntu:latest
chown -R 185:185 /mnt/data
chown -R 185:185 /mnt/spark-events
```
2. Download _databio-8p_ dataset and put it into _databio-8p_ directory on the _spark-job-data_
    docker volume.
```shell
ls docker/volumes/spark-job-data
# benchmark  databio-8p synthetic
ls docker/volumes/spark-job-data/databio-8p
# chainMonDom5Link chainRn4         chainXenTro3Link ex-rna           fBrain
# chainOrnAna1     chainVicPac2     ex-anno          exons
```
3. Build docker image for the benchmark application.
```shell
sbt docker
```
4. Run Apache Spark cluster from the _docker-compose.yaml_ file.
```shell
docker compose up --detach spark-master
docker compose up --detach spark-worker
docker compose up --detach spark-history-server
```
5. Run the benchmarking job.
```shell
docker compose up spark-driver
```