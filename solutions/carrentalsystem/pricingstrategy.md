```java
// Pricing Strategy Interface
interface PricingStrategy {
    double calculatePrice(Car car, LocalDateTime startTime, LocalDateTime endTime);
}

// Basic Hourly Pricing
class HourlyPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Car car, LocalDateTime startTime, LocalDateTime endTime) {
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        return hours * car.getHourlyRate();
    }
}

// Peak Hours Pricing
class PeakHoursPricingStrategy implements PricingStrategy {
    private static final int PEAK_START_HOUR = 9;
    private static final int PEAK_END_HOUR = 17;
    private static final double PEAK_MULTIPLIER = 1.5;
    
    @Override
    public double calculatePrice(Car car, LocalDateTime startTime, LocalDateTime endTime) {
        double totalPrice = 0;
        LocalDateTime current = startTime;
        
        while (current.isBefore(endTime)) {
            int hour = current.getHour();
            boolean isPeakHour = hour >= PEAK_START_HOUR && hour < PEAK_END_HOUR;
            double hourlyRate = car.getHourlyRate();
            
            if (isPeakHour) {
                hourlyRate *= PEAK_MULTIPLIER;
            }
            
            totalPrice += hourlyRate;
            current = current.plusHours(1);
        }
        
        return totalPrice;
    }
}

// Weekend Pricing
class WeekendPricingStrategy implements PricingStrategy {
    private static final double WEEKEND_MULTIPLIER = 1.3;
    
    @Override
    public double calculatePrice(Car car, LocalDateTime startTime, LocalDateTime endTime) {
        double totalPrice = 0;
        LocalDateTime current = startTime;
        
        while (current.isBefore(endTime)) {
            boolean isWeekend = current.getDayOfWeek() == DayOfWeek.SATURDAY || 
                              current.getDayOfWeek() == DayOfWeek.SUNDAY;
            double hourlyRate = car.getHourlyRate();
            
            if (isWeekend) {
                hourlyRate *= WEEKEND_MULTIPLIER;
            }
            
            totalPrice += hourlyRate;
            current = current.plusHours(1);
        }
        
        return totalPrice;
    }
}

// Seasonal Pricing
class SeasonalPricingStrategy implements PricingStrategy {
    private Map<Month, Double> seasonalMultipliers;
    
    public SeasonalPricingStrategy() {
        seasonalMultipliers = new HashMap<>();
        seasonalMultipliers.put(Month.JULY, 1.4);
        seasonalMultipliers.put(Month.AUGUST, 1.4);
        seasonalMultipliers.put(Month.DECEMBER, 1.3);
    }
    
    @Override
    public double calculatePrice(Car car, LocalDateTime startTime, LocalDateTime endTime) {
        double multiplier = seasonalMultipliers.getOrDefault(startTime.getMonth(), 1.0);
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        return hours * car.getHourlyRate() * multiplier;
    }
}

// Composite Pricing Strategy
class CompositePricingStrategy implements PricingStrategy {
    private List<PricingStrategy> strategies;
    
    public CompositePricingStrategy() {
        this.strategies = new ArrayList<>();
    }
    
    public void addStrategy(PricingStrategy strategy) {
        strategies.add(strategy);
    }
    
    @Override
    public double calculatePrice(Car car, LocalDateTime startTime, LocalDateTime endTime) {
        // Take the maximum price from all strategies
        return strategies.stream()
                .mapToDouble(strategy -> strategy.calculatePrice(car, startTime, endTime))
                .max()
                .orElse(0.0);
    }
}

// Update Reservation class to use pricing strategy
class Reservation {
    // ... other fields ...
    private PricingStrategy pricingStrategy;
    
    public Reservation(String id, Car car, User user, 
                      LocalDateTime startTime, LocalDateTime endTime,
                      PricingStrategy pricingStrategy) {
        this.id = id;
        this.car = car;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pricingStrategy = pricingStrategy;
    }
    
    public double calculatePrice() {
        return pricingStrategy.calculatePrice(car, startTime, endTime);
    }
}

// Update CarRentalSystem to handle pricing strategies
class CarRentalSystem {
    // ... other fields ...
    private Map<CarType, PricingStrategy> pricingStrategies;
    
    public CarRentalSystem() {
        // ... other initializations ...
        this.pricingStrategies = new HashMap<>();
        initializePricingStrategies();
    }
    
    private void initializePricingStrategies() {
        // For luxury cars, use composite strategy with peak hours and weekend pricing
        CompositePricingStrategy luxuryStrategy = new CompositePricingStrategy();
        luxuryStrategy.addStrategy(new PeakHoursPricingStrategy());
        luxuryStrategy.addStrategy(new WeekendPricingStrategy());
        
        pricingStrategies.put(CarType.LUXURY, luxuryStrategy);
        
        // For economy cars, use basic hourly pricing
        pricingStrategies.put(CarType.ECONOMY, new HourlyPricingStrategy());
        
        // For SUVs, use seasonal pricing
        pricingStrategies.put(CarType.SUV, new SeasonalPricingStrategy());
    }
    
    public Reservation makeReservation(String userId, String carId,
                                     LocalDateTime startTime, 
                                     LocalDateTime endTime) {
        User user = users.get(userId);
        Car car = cars.get(carId);
        
        if (user == null || car == null || 
            !isCarAvailable(car, startTime, endTime)) {
            throw new IllegalArgumentException("Invalid reservation request");
        }
        
        PricingStrategy strategy = pricingStrategies.getOrDefault(
            car.getType(), new HourlyPricingStrategy());
            
        Reservation reservation = new Reservation(
            generateId(), car, user, startTime, endTime, strategy);
        reservation.setTotalPrice(reservation.calculatePrice());
        
        reservations.put(reservation.getId(), reservation);
        car.setStatus(CarStatus.RESERVED);
        
        return reservation;
    }
}
```