package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.InvalidStateException;
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

    ApplicationContext getApplicationContext();

    default void printParentMenu() {
        System.out.println();
        System.out.println("Available services:");
        Set<Class<?>> services = operations.keySet();
        int i = 1;
        for (Class<?> service : services) {
            System.out.println("\t" + (i++) + " - " + service.getSimpleName());
        }
    }

    default void printOperationMenu(Class<?> service) {
        List<Method> options = operations.get(service);
        if (options == null) return;
        System.out.println();
        System.out.println(service.getSimpleName() + " operations:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("\t" + (i + 1) + " - " + options.get(i).getAnnotation(MethodInfo.class).description());
        }
        System.out.println("\t" + (options.size() + 1) + " - Back");
    }

    default Command handleUserInput() throws InvalidStateException, IOException {
        Scanner scanner = new Scanner(System.in);
        printParentMenu();
        int serviceNum = scanner.nextInt() - 1;
        Command command;

        switch (serviceNum) {
            //CrewService
            case 0: {
                printOperationMenu(CrewService.class);
                int operationNum = scanner.nextInt();
                scanner.nextLine();
                command = handleCrewService(operationNum, scanner);
                break;
            }
            //SpaceshipService
            case 1: {
                printOperationMenu(SpaceshipService.class);
                int operationNum = scanner.nextInt();
                scanner.nextLine();
                command = handleSpaceshipService(operationNum, scanner);
                break;
            }
            //SpacemapService
            case 2: {
                printOperationMenu(SpacemapService.class);
                int operationNum = scanner.nextInt();
                scanner.nextLine();
                command = handleSpacemapService(operationNum, scanner);
                break;
            }
            case 3: {
                printOperationMenu(MissionService.class);
                int operationNum = scanner.nextInt();
                scanner.nextLine();
                command = handleMissionService(operationNum, scanner);
                break;
            }
            default: {
                return null;
            }
        }
        return command;
    }

    default Command handleCrewService(int operation, Scanner scanner) {
        CrewService service = CrewServiceImpl.getInstance();
        Command command;
        switch (operation) {
            //findAllCrewMembers
            case 1: {
                command = () -> System.out.println(StringUtils.join(service.findAllCrewMembers(), "\n"));
                break;
            }
            //findAllCrewMembersByCriteria
            case 2: {
                System.out.println("Criteria template - id=..;name=..;rank=..;role=..;isReady=..");
                System.out.print("Your criteria: ");
                command = () -> {
                    Criteria<CrewMember> criteria = CrewMemberCriteria.parseCriteria(scanner.nextLine());
                    Collection<CrewMember> foundMembers = service.findAllCrewMembersByCriteria(criteria);
                    if (!foundMembers.isEmpty()) {
                        System.out.println(StringUtils.join(foundMembers, "\n"));
                    } else {
                        System.out.println("No members found by given criteria: " + criteria);
                    }
                };
                break;
            }
            //findCrewMemberByCriteria
            case 3: {
                System.out.println("Criteria template - id=..;name=..;rank=..;role=..;isReady=..");
                System.out.print("Your criteria: ");
                command = () -> {
                    Criteria<CrewMember> criteria = CrewMemberCriteria.parseCriteria(scanner.nextLine());
                    Optional<CrewMember> foundMember = service.findCrewMemberByCriteria(criteria);
                    if (foundMember.isPresent()) {
                        System.out.println(foundMember.get());
                    } else {
                        System.out.println("No member found by given criteria: " + criteria);
                    }
                };
                break;
            }
            //createCrewMember
            case 4: {
                System.out.println("Crew member template - role,name,rank");
                System.out.print("Your crew member: ");
                command = () -> {
                    CrewMember member = new CrewMemberFactory().create(scanner.nextLine());
                    System.out.println(service.createCrewMember(member) + " created successfully");
                };
                break;
            }
            //deleteCrewMember
            case 5: {
                System.out.println("Enter crew member id: ");
                command = () -> {
                    Long id = scanner.nextLong();
                    service.deleteCrewMember(
                            service.findCrewMemberByCriteria(CrewMemberCriteria.newBuilder().setId(id).build())
                                    .orElseThrow(() -> new EntityNotFoundException("CrewMember", id))
                    );
                    System.out.println("Deleted successfully");
                };
                break;
            }
            //Exit
            default: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default Command handleSpaceshipService(int operation, Scanner scanner) {
        SpaceshipService service = SpaceshipServiceImpl.getInstance();
        Command command;
        switch (operation) {
            //findAllSpaceships
            case 1: {
                command = () -> System.out.println(StringUtils.join(service.findAllSpaceships(), "\n"));
                break;
            }
            //findAllSpaceshipsByCriteria
            case 2: {
                System.out.println("Criteria template - id=..;name=..;crew={roleId:count,..};distance=..;isReady=..");
                System.out.print("Your criteria: ");
                Criteria<Spaceship> criteria = SpaceshipCriteria.parseCriteria(scanner.nextLine());
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
                System.out.println("Criteria template - id=..;name=..;crew={roleId:count,..};distance=..;isReady=..");
                System.out.print("Your criteria: ");
                Criteria<Spaceship> criteria = SpaceshipCriteria.parseCriteria(scanner.nextLine());
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
                Spaceship spaceship = new SpaceshipFactory().create(scanner.nextLine());
                command = () -> System.out.println(service.createSpaceship(spaceship) + " created successfully");
                break;
            }
            //deleteSpaceship
            case 5: {
                System.out.println("Enter spaceship id: ");
                Long id = scanner.nextLong();
                command = () -> {
                    service.deleteSpaceship(
                            service.findSpaceshipByCriteria(SpaceshipCriteria.newBuilder().setId(id).build())
                                    .orElseThrow(() -> new EntityNotFoundException("Spaceship", id))
                    );
                    System.out.println("Deleted successfully");
                };
                break;
            }
            //Exit
            default: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default Command handleSpacemapService(int operation, Scanner scanner) {
        SpacemapService service = SpacemapServiceImpl.getInstance();
        Command command;
        switch (operation) {
            //findAllPlanets
            case 1: {
                command = () -> System.out.println(StringUtils.join(service.findAll(), "\n"));
                break;
            }
            //getRandomPlanet
            case 2: {
                command = () -> System.out.println(service.getRandomPlanet());
                break;
            }
            //getDistanceBetweenPlanets
            case 3: {
                System.out.print("Enter ids of planets: ");
                Long id1 = scanner.nextLong();
                Long id2 = scanner.nextLong();
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
            default: {
                return this::handleUserInput;
            }
        }
        return command;
    }

    default Command handleMissionService(int operation, Scanner scanner) throws InvalidStateException, IOException {
        MissionService service = MissionServiceImpl.getInstance();
        ApplicationProperties properties = ApplicationProperties.getInstance();

        Path outputDir = Paths.get(properties.getOutputRootDir() + "/");
        File missionOutput = new File(outputDir + "/" + properties.getMissionFileName() + ".json");
        if (!missionOutput.exists() && !missionOutput.createNewFile())
            throw new InvalidStateException("output file cannot be created");

        ObjectMapper mapper = new ObjectMapper();
        Command command;
        switch (operation) {
            //findAllMissions
            case 1: {
                command = () -> {
                    Collection<FlightMission> missions = service.findAllMissions();
                    mapper.writeValue(missionOutput, missions);
                    System.out.println(StringUtils.join(missions, "\n"));
                };
                break;
            }
            //findAllMissionsByCriteria
            case 2: {
                command = () -> {
                    System.out.println("Criteria template - id=..;name=..;startDate=..;endDate=..;" +
                            "distance=..;spaceship=(id);crew=id1,id2,..;status=..;from=(id);to=(id)");
                    System.out.print("Your criteria: ");
                    FlightMissionCriteria criteria = FlightMissionCriteria.parseCriteria(scanner.nextLine());
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
                command = () -> {
                    System.out.println("Criteria template - id=..;name=..;startDate=..;endDate=..;" +
                            "distance=..;spaceship=(id);crew=id1,id2,..;status=..;from=(id);to=(id)");
                    System.out.print("Your criteria: ");
                    FlightMissionCriteria criteria = FlightMissionCriteria.parseCriteria(scanner.nextLine());
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
                command = () -> {
                    System.out.print("Enter mission and crew member ids: ");

                    Long missionId = scanner.nextLong();
                    FlightMission mission = service.findMissionByCriteria(
                            FlightMissionCriteria.newBuilder().setId(missionId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Flight Mission", missionId));

                    Long memberId = scanner.nextLong();
                    CrewMember member = CrewServiceImpl.getInstance().findCrewMemberByCriteria(
                            CrewMemberCriteria.newBuilder().setId(memberId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Crew Member", missionId));

                    service.assignCrewMemberOnMission(mission, member);
                };
                break;
            }
            //assignSpaceship
            case 5: {
                command = () -> {
                    System.out.print("Enter mission and spaceship ids: ");

                    Long missionId = scanner.nextLong();
                    FlightMission mission = service.findMissionByCriteria(
                            FlightMissionCriteria.newBuilder().setId(missionId).build()
                    ).orElseThrow(() -> new EntityNotFoundException("Flight Mission", missionId));

                    Long shipId = scanner.nextLong();
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
                command = () -> {
                    FlightMission mission = new FlightMissionFactory().create(scanner.nextLine());
                    System.out.println(service.createMission(mission) + " created successfully");
                };
                break;
            }
            //Exit
            default: {
                return this::handleUserInput;
            }
        }
        return command;
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