# investmentFarm
Investment game based on the Tinkoff API

To run you need to provide your sandbox token in the TinkoffService class.

![image](https://user-images.githubusercontent.com/34129451/169921681-dfc2a6f5-e14a-494b-9db0-f005820570c4.png)
Herds of animals will grow or lessen depending on the ratio of the current value of the currency to its average value over the last minute.
If the current value exceed the average up to 1.5, then the herd will be red colored. Or if it'll be less than 0.5 of the average, then the herd will be colored by green. Green means time to buy. Red - time to sell. Just click on the herd you are interested in, then click on the arrow 'up' or 'down'.
The 100% step between two currency values is 0.1. It means that if the currency value is bigger than average on 0.2, then the herd will be scaled up to x2.
