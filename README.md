# Phone Company

Each day at The Phone Company a batch job puts all the customer calls for the previous day into a single log file of:

`'customer id','phone number called','call duration'`

For a customer the cost of a call up to and including 3 minutes in duration is charged at 0.05p/sec, any call over 3 minutes in duration the additional time is charged at 0.03p/sec. However, there is a promotion on and the calls made to the phone number with the greatest total cost is removed from the customer's bill.

## Task

Write a program that when run will parse the `calls.log` file and print out the total cost of calls for the day for each customer. You can use any libraries you wish to.

## Build

    sbt universal:packageZipTarball
    
## Run on Linux

Unpack disco-test-phone-company-1.0.tgz

    tar -zxvf target/universal/disco-test-phone-company-1.0.tgz -C /tmp

Run application by script disco-test-phone-company

    /tmp/disco-test-phone-company-1.0/bin/disco-test-phone-company
    
Application will read calls.log from classpath if no program arguments specified,
or the first argument will be used as filename.

    /tmp/disco-test-phone-company-1.0/bin/disco-test-phone-company src/main/resources/calls.log
