package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@FunctionalInterface
public interface ApplicationMenu {
    Map<Class<?>, List<Method>> operations = new LinkedHashMap<Class<?>, List<Method>>() {{
        put(CrewService.class, Arrays.stream(CrewService.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MethodInfo.class))
                .sorted(new MethodComparator())
                .collect(Collectors.toList()));
        put(SpaceshipService.class, Arrays.stream(SpaceshipService.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MethodInfo.class))
                .sorted(new MethodComparator())
                .collect(Collectors.toList()));
        put(SpacemapService.class, Arrays.stream(SpacemapService.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MethodInfo.class))
                .sorted(new MethodComparator())
                .collect(Collectors.toList()));
        put(MissionService.class, Arrays.stream(MissionService.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MethodInfo.class))
                .sorted(new MethodComparator())
                .collect(Collectors.toList()));
    }};

    Scanner scanner = new Scanner(System.in);

    ApplicationContext getApplicationContext();

    default void printParentMenu() {
        System.out.println("--------------------------------------");
        System.out.println("Available services:");
        Set<Class<?>> services = operations.keySet();
        int i = 1;
        for (Class<?> service : services) {
            System.out.println("\t" + (i++) + " - " + service.getSimpleName());
        }
        System.out.println("\t" + (services.size() + 1) + " - Exit");
        System.out.println("--------------------------------------");
        System.out.print("Your service: ");
    }

    default void printOperationMenu(Class<?> service) {
        List<Method> options = operations.get(service);
        if (options == null) return;
        System.out.println("--------------------------------------");
        System.out.println(service.getSimpleName() + " operations:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("\t" + (i + 1) + " - " + options.get(i).getAnnotation(MethodInfo.class).description());
        }
        System.out.println("\t" + (options.size() + 1) + " - Back");
        System.out.println("--------------------------------------");
        System.out.print("Your operation: ");
    }

    default Command handleUserInput() throws InvalidStateException, IOException {
        printParentMenu();
        int serviceNum;
        Command command = null;
        while (true) {
            try {
                serviceNum = scanner.nextInt();
                scanner.nextLine();
                while (serviceNum > 5 || serviceNum < 1) {
                    System.out.println("There is no service with number " + serviceNum + ". Try again.");
                    System.out.print("Your service: ");
                    serviceNum = scanner.nextInt();
                    scanner.nextLine();
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Enter number of service.");
                System.out.print("Your service: ");
            }
        }
        switch (serviceNum) {
            //CrewService
            case 1: {
                printOperationMenu(CrewService.class);
                command = handleCrewService();
                break;
            }
            //SpaceshipService
            case 2: {
                printOperationMenu(SpaceshipService.class);
                command = handleSpaceshipService();
                break;
            }
            //SpacemapService
            case 3: {
                printOperationMenu(SpacemapService.class);
                command = handleSpacemapService();
                break;
            }
            //MissionService
            case 4: {
                printOperationMenu(MissionService.class);
                command = handleMissionService();
                break;
            }
        }
        return command;
    }

    default Command handleCrewService() {
        CrewService service = CrewServiceImpl.getInstance();
        Command command = null;
        switch (parseOperation(1, 6)) {
            //findAllCrewMembers
            case 1: {
                command = () -> {
                    Collection<CrewMember> crewMembers = service.findAllCrewMembers();
                    if (crewMembers.isEmpty()){
                        System.out.println("There are no crew members yet");
                        return;
                    }
                    System.out.println(StringUtils.join(crewMembers, "\n"));
                };
                break;
            }
            //findAllCrewMembersByCriteria
            case 2: {
                Criteria<CrewMember> finalCriteria = parseCrewMemberCriteria();
                command = () -> {
                    Collection<CrewMember> foundMembers = service.findAllCrewMembersByCriteria(finalCriteria);
                    if (!foundMembers.isEmpty()) {
                        System.out.println(StringUtils.join(foundMembers, "\n"));
                    } else {
                        System.out.println("No members found by given criteria: " + finalCriteria);
                    }
                };
                break;
            }
            //findCrewMemberByCriteria
            case 3: {
                Criteria<CrewMember> finalCriteria = parseCrewMemberCriteria();
                command = () -> {
                    Optional<CrewMember> foundMember = service.findCrewMemberByCriteria(finalCriteria);
                    if (foundMember.isPresent()) {
                        System.out.println(foundMember.get());
                    } else {
                        System.out.println("No member found by given criteria: " + finalCriteria);
                    }
                };
                break;
            }
            //createCrewMember
            case 4: {
                System.out.println("Crew member template - role,name,rank");
                System.out.print("Your crew member: ");
                CrewMemberFactory factory = new CrewMemberFactory();
                CrewMember member;
                while (true) {
                    try {
                        member = factory.create(scanner.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.print("Your crew member: ");
                    }
                }
                CrewMember finalMember = member;
                command = () -> System.out.println(service.createCrewMember(finalMember) + " created successfully");
                break;
            }
            //deleteCrewMember
            case 5: {
                Long finalId = parseId("crew member");
                command = () -> {
                    service.deleteCrewMember(
                            service.findCrewMemberByCriteria(CrewMemberCriteria.newBuilder().setId(finalId).build())
                                    .orElseThrow(() -> new EntityNotFoundException("CrewMember", finalId))
                    );
                    System.out.println("Deleted successfully");
                };
                break;
            }
            //Exit
            case 6: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default Command handleSpaceshipService() {
        SpaceshipService service = SpaceshipServiceImpl.getInstance();
        Command command = null;
        switch (parseOperation(1, 5)) {
            //findAllSpaceships
            case 1: {
                command = () -> {
                    Collection<Spaceship> spaceships = service.findAllSpaceships();
                    if(spaceships.isEmpty()) {
                        System.out.println("There are no spaceships yet");
                        return;
                    }
                    System.out.println(StringUtils.join(spaceships, "\n"));
                };
                break;
            }
            //findAllSpaceshipsByCriteria
            case 2: {
                Criteria<Spaceship> criteria = parseSpaceshipCriteria();
                command = () -> {
                    Collection<Spaceship> foundSpaceships = service.findAllSpaceshipsByCriteria(criteria);
                    if (!foundSpaceships.isEmpty()) {
                        System.out.println(StringUtils.join(foundSpaceships, "\n"));
                    } else {
                        System.out.println("No spaceships found by given criteria: " + criteria);
                    }
                };
                break;
            }
            //findSpaceshipByCriteria
            case 3: {
                Criteria<Spaceship> criteria = parseSpaceshipCriteria();
                command = () -> {
                    Optional<Spaceship> foundSpaceship = service.findSpaceshipByCriteria(criteria);
                    if (foundSpaceship.isPresent()) {
                        System.out.println(foundSpaceship.get());
                    } else {
                        System.out.println("No spaceship found by given criteria: " + criteria);
                    }
                };
                break;
            }
            //createSpaceship
            case 4: {
                System.out.println("Spaceship template - name;distance;crew {roleId:count,..}");
                System.out.print("Your spaceship: ");
                SpaceshipFactory factory = new SpaceshipFactory();
                Spaceship spaceship;
                while (true) {
                    try {
                        spaceship = factory.create(scanner.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.print("Your spaceship: ");
                    }
                }
                Spaceship finalShip = spaceship;
                command = () -> System.out.println(service.createSpaceship(finalShip) + " created successfully");
                break;
            }
            //deleteSpaceship
            case 5: {
                long finalId = parseId("spaceship");
                command = () -> {
                    service.deleteSpaceship(
                            service.findSpaceshipByCriteria(SpaceshipCriteria.newBuilder().setId(finalId).build())
                                    .orElseThrow(() -> new EntityNotFoundException("Spaceship", finalId))
                    );
                    System.out.println("Deleted successfully");
                };
                break;
            }
            //Exit
            case 6: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default Command handleSpacemapService() {
        SpacemapService service = SpacemapServiceImpl.getInstance();
        Command command = null;
        switch (parseOperation(1, 4)) {
            //findAllPlanets
            case 1: {
                command = () -> {
                    Collection<Planet> planets = service.findAll();
                    if (planets.isEmpty()) {
                        System.out.println("There are no planets");
                        return;
                    }
                    System.out.println(StringUtils.join(planets, "\n"));
                };
                break;
            }
            //getRandomPlanet
            case 2: {
                command = () -> System.out.println(service.getRandomPlanet());
                break;
            }
            //getDistanceBetweenPlanets
            case 3: {
                Long id1 = parseId("first planet");
                Long id2 = parseId("second planet");
                command = () -> {
                    Optional<Planet> from = service.findPlanetById(id1);
                    if (!from.isPresent()) {
                        System.out.println("Planet with id " + id1 + " not found");
                        return;
                    }
                    Optional<Planet> to = service.findPlanetById(id2);
                    if (!to.isPresent()) {
                        System.out.println("Planet with id " + id2 + " not found");
                        return;
                    }
                    System.out.println(service.getDistanceBetweenPlanets(from.get(), to.get()));
                };
                break;
            }
            //Exit
            case 4: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default Command handleMissionService() throws InvalidStateException, IOException {
        MissionService service = MissionServiceImpl.getInstance();
        ApplicationProperties properties = ApplicationProperties.getInstance();

        Path outputDir = Paths.get(properties.getOutputRootDir() + "/");
        if (!outputDir.toFile().exists()) {
            outputDir.toFile().mkdir();
        }
        File missionOutput = new File(outputDir + "/" + properties.getMissionFileName() + ".json");
        if (!missionOutput.exists() && !missionOutput.createNewFile())
            throw new InvalidStateException("output file cannot be created");

        ObjectMapper mapper = new ObjectMapper();
        Command command = null;
        switch (parseOperation(1, 7)) {
            //findAllMissions
            case 1: {
                command = () -> {
                    Collection<FlightMission> missions = service.findAllMissions();
                    if (missions.isEmpty()) {
                        System.out.println("There are no missions yet");
                        return;
                    }
                    mapper.writeValue(missionOutput, missions);
                    System.out.println(StringUtils.join(missions, "\n"));
                };
                break;
            }
            //findAllMissionsByCriteria
            case 2: {
                Criteria<FlightMission> criteria = parseMissionCriteria();
                command = () -> {
                    Collection<FlightMission> missions = service.findAllMissionsByCriteria(criteria);
                    if (!missions.isEmpty()) {
                        mapper.writeValue(missionOutput, missions);
                        System.out.println(StringUtils.join(missions, "\n"));
                    } else {
                        System.out.println("No missions found by given criteria: " + criteria);
                    }
                };
                break;
            }
            //findMissionByCriteria
            case 3: {
                Criteria<FlightMission> criteria = parseMissionCriteria();
                command = () -> {
                    Optional<FlightMission> mission = service.findMissionByCriteria(criteria);
                    if (mission.isPresent()) {
                        mapper.writeValue(missionOutput, mission);
                        System.out.println(StringUtils.join(mission.get(), "\n"));
                    } else {
                        System.out.println("No mission found by given criteria: " + criteria);
                    }
                };
                break;
            }
            //assignCrewMember
            case 4: {
                long missionId = parseId("mission");
                long memberId = parseId("crew member");
                command = () -> {
                    FlightMission mission = service.findMissionByCriteria(
                            FlightMissionCriteria.newBuilder().setId(missionId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Flight Mission", missionId));

                    CrewMember member = CrewServiceImpl.getInstance().findCrewMemberByCriteria(
                            CrewMemberCriteria.newBuilder().setId(memberId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Crew Member", missionId));

                    service.assignCrewMemberOnMission(mission, member);
                };
                break;
            }
            //assignSpaceship
            case 5: {
                long missionId = parseId("mission");
                long shipId = parseId("spaceship");
                command = () -> {
                    FlightMission mission = service.findMissionByCriteria(
                            FlightMissionCriteria.newBuilder().setId(missionId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Flight Mission", missionId));

                    Spaceship ship = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(
                            SpaceshipCriteria.newBuilder().setId(shipId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Spaceship", shipId));

                    service.assignSpaceshipOnMission(mission, ship);
                };
                break;
            }
            //createMission
            case 6: {
                System.out.println("Mission template - name,startDate,fromId,toId");
                System.out.print("Your mission: ");
                FlightMissionFactory factory = new FlightMissionFactory();
                FlightMission mission;
                while (true) {
                    try {
                        mission = factory.create(scanner.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.print("Your mission: ");
                    }
                }
                FlightMission finalMission = mission;
                command = () -> System.out.println(service.createMission(finalMission) + " created successfully");
                break;
            }
            //Exit
            case 7: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default int parseOperation(int leftBound, int rightBound) {
        int operation;
        while (true) {
            try {
                operation = scanner.nextInt();
                scanner.nextLine();
                while (operation < leftBound || operation > rightBound) {
                    System.out.println("There is no operation with number " + operation + ". Try again.");
                    System.out.print("Your operation: ");
                    operation = scanner.nextInt();
                    scanner.nextLine();
                }
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Enter number of operation.");
                System.out.print("Your operation: ");
            }
        }
        return operation;
    }

    default Criteria<CrewMember> parseCrewMemberCriteria(){
        System.out.println("Criteria template - id=..;name=..;rank=..;role=..;isReady=..");
        System.out.print("Your criteria: ");
        Criteria<CrewMember> criteria;
        while (true) {
            try {
                criteria = CrewMemberCriteria.parseCriteria(scanner.nextLine());
                break;
            } catch (IllegalArgumentException | UnknownEntityException e) {
                System.out.println(e.getMessage());
                System.out.print("Your criteria: ");
            }
        }
        return criteria;
    }

    default Criteria<Spaceship> parseSpaceshipCriteria(){
        System.out.println("Criteria template - id=..;name=..;crew={roleId:count,..};distance=..;isReady=..");
        System.out.print("Your criteria: ");
        Criteria<Spaceship> criteria;
        while (true) {
            try {
                criteria = SpaceshipCriteria.parseCriteria(scanner.nextLine());
                break;
            } catch (IllegalArgumentException | UnknownEntityException e) {
                System.out.println(e.getMessage());
                System.out.print("Your criteria: ");
            }
        }
        return criteria;
    }

    default Criteria<FlightMission> parseMissionCriteria(){
        System.out.println("Criteria template - id=..;name=..;startDate=..;endDate=..;" +
                "distance=..;spaceship=(id);crew=id1,id2,..;status=..;from=(id);to=(id)");
        System.out.print("Your criteria: ");
        Criteria<FlightMission> criteria;
        while (true) {
            try {
                criteria = FlightMissionCriteria.parseCriteria(scanner.nextLine());
                break;
            } catch (IllegalArgumentException | EntityNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.print("Your criteria: ");
            }
        }
        return criteria;
    }

    default long parseId(String msg){
        String str = "Enter " + msg + " id: ";
        System.out.print(str);
        long id;
        while (true) {
            try {
                id = scanner.nextLong();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Enter id(long).");
                System.out.print(str);
            }
        }
        return id;
    }
}

class MethodComparator implements Comparator<Method> {
    @Override
    public int compare(Method o1, Method o2) {
        int pos1 = o1.getAnnotation(MethodInfo.class).menuPosition();
        int pos2 = o2.getAnnotation(MethodInfo.class).menuPosition();
        return Integer.compare(pos1, pos2);
    }
}